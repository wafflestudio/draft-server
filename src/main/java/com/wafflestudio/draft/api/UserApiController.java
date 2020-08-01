package com.wafflestudio.draft.api;


import com.wafflestudio.draft.dto.request.*;
import com.wafflestudio.draft.dto.response.DeviceResponse;
import com.wafflestudio.draft.dto.response.PreferenceInRegionResponse;
import com.wafflestudio.draft.dto.response.RoomsOfUserResponse;
import com.wafflestudio.draft.dto.response.UserInformationResponse;
import com.wafflestudio.draft.model.*;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.security.JwtTokenProvider;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.password.UserPrincipal;
import com.wafflestudio.draft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApiController {

    private final OAuth2Provider oAuth2Provider;
    private final AuthUserService authUserService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RegionService regionService;
    private final PreferenceService preferenceService;
    private final DeviceService deviceService;
    private final RoomService roomService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup/")
    public ResponseEntity<User> createUser(@RequestBody @Valid SignUpRequest signUpRequest, HttpServletResponse response) throws IOException {
        User user;
        String username = signUpRequest.getUsername();

        if (userService.existsUserByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        switch (signUpRequest.getGrantType()) {
            case "OAUTH":
                OAuth2Response oAuth2Response = oAuth2Provider.requestAuthentication(signUpRequest);
                if (oAuth2Response.getStatus() != HttpStatus.OK) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token for auth server is not valid");
                    return null;
                }

                user = new User(username, oAuth2Response.getEmail());
                break;
            case "PASSWORD":
                if (username == null || signUpRequest.getEmail() == null || signUpRequest.getPassword() == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return null;
                }

                user = new User(username, signUpRequest.getEmail());
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Grant type not valid");
                return null;
        }

        authUserService.saveUser(user);
        response.addHeader("Authentication", jwtTokenProvider.generateToken(user.getEmail()));

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/check-username/")
    public ResponseEntity<Void> checkUsername(@RequestBody @Valid CheckUsernameRequest checkUsernameRequest) {
        if (userService.existsUserByUsername(checkUsernameRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me/")
    public ResponseEntity<UserInformationResponse> myInfo(@CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(new UserInformationResponse(currentUser), HttpStatus.OK);
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/info/")
    public List<PreferenceInRegionResponse> setPreferences(@RequestBody @Valid List<SetPreferenceRequest> preferenceRequestList, @CurrentUser UserPrincipal currentUser) {
        List<PreferenceInRegionResponse> preferenceInRegionResponses = new ArrayList<>();

        for (SetPreferenceRequest preferenceRequest : preferenceRequestList) {
            Optional<Region> region = regionService.findRegionById(preferenceRequest.getRegionId());
            if (region.isEmpty()) {
                // FIXME: when a region is not found, we should not apply whole preferences of the request
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            List<Preference> preferences = preferenceRequest.getPreferences();
            preferenceService.setPreferences(currentUser, region.get(), preferences);
            preferenceInRegionResponses.add(new PreferenceInRegionResponse(region.get(), preferences));
        }

        return preferenceInRegionResponses;
    }

    @GetMapping("/playable/")
    public List<Long> getPlayableUsers(@Valid @ModelAttribute GetUsersByPreferenceRequest getUsersByPreferenceRequest) {
        String regionName = getUsersByPreferenceRequest.getRegionName();
        DayOfWeek dayOfWeek = getUsersByPreferenceRequest.getDayOfWeek();
        LocalTime startTime = getUsersByPreferenceRequest.getStartTime();
        LocalTime endTime = getUsersByPreferenceRequest.getEndTime();
        return preferenceService.getPlayableUsers(regionName, dayOfWeek, startTime, endTime);
    }

    @PostMapping("/device/")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse setDevice(@RequestBody @Valid SetDeviceRequest request, @CurrentUser User currentUser) {
        Device device = new Device();
        device.setUser(currentUser);
        device.setDeviceToken(request.getDeviceToken());
        deviceService.create(device);
        return new DeviceResponse(device);
    }

    @GetMapping("/room/")
    public RoomsOfUserResponse getBelongingRooms(@CurrentUser User currentUser) {
        List<Room> rooms = roomService.findRoomsByUser(currentUser);
        return new RoomsOfUserResponse(currentUser, rooms);
    }
}
