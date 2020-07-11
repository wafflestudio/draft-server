package com.wafflestudio.draft.api;


import com.wafflestudio.draft.model.*;
import com.wafflestudio.draft.model.request.SignUpRequest;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.password.UserPrincipal;
import com.wafflestudio.draft.service.DeviceService;
import com.wafflestudio.draft.service.PreferenceService;
import com.wafflestudio.draft.service.RegionService;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private final PasswordEncoder passwordEncoder;
    private final RegionService regionService;
    private final PreferenceService preferenceService;
    private final DeviceService deviceService;

    @PostMapping("/signup/")
    public ResponseEntity<User> createUser(@RequestBody SignUpRequest signUpRequest, HttpServletResponse response) throws IOException {
        User user;

        switch (signUpRequest.getGrantType()) {
            case "OAUTH":
                OAuth2Response oAuth2Response = oAuth2Provider.requestAuthentication(signUpRequest);
                if (oAuth2Response.getStatus() != HttpStatus.OK) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token for auth server is not valid");
                    return null;
                }

                user = new User(signUpRequest.getUsername(), oAuth2Response.getEmail());
                break;
            case "PASSWORD":
                if (signUpRequest.getUsername() == null || signUpRequest.getEmail() == null || signUpRequest.getPassword() == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return null;
                }

                user = new User(signUpRequest.getUsername(), signUpRequest.getEmail());
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Grant type not valid");
                return null;
        }

        authUserService.saveUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/me/")
    public ResponseEntity<GetUserInformationResponse> myInfo(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser);
        return new ResponseEntity<>(new GetUserInformationResponse(currentUser), HttpStatus.OK);
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/info/")
    public List<PreferenceInRegionResponse> setPreferences(@Valid @RequestBody List<SetPreferenceRequest> preferenceRequestList, @CurrentUser UserPrincipal currentUser) {
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
    public DeviceResponse setDevice(@RequestBody @Valid SetDeivceRequest request, @CurrentUser User currentUser) {
        Device device = new Device();
        device.setUser(currentUser);
        device.setDeviceToken(request.deviceToken);
        deviceService.create(device);
        return new DeviceResponse(device);
    }

    @Data
    static class SetPreferenceRequest {
        private Long regionId;
        private List<Preference> preferences;
    }

    @Data
    static class GetUserInformationResponse {
        private Long id;
        private String username;
        private String email;

        public GetUserInformationResponse(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Data
    static class GetUsersByPreferenceRequest {
        private String regionName;
        private DayOfWeek dayOfWeek;
        @DateTimeFormat(pattern = "HHmmss")
        private LocalTime startTime;
        @DateTimeFormat(pattern = "HHmmss")
        private LocalTime endTime;
    }

    @Data
    static class SetDeivceRequest {
        @NotNull
        private String deviceToken;
    }

    @Data
    static class DeviceResponse {
        private Long id;
        private String deviceToken;
        private Long userId;
        private String username;

        public DeviceResponse(Device device) {
            this.id = device.getId();
            this.deviceToken = device.getDeviceToken();
            this.userId = device.getUser().getId();
            this.username = device.getUser().getUsername();
        }
    }

    @Data
    static class PreferenceResponse {
        private Long id;
        private LocalTime startAt;
        private LocalTime endAt;
        private Long regionId;
        private DayOfWeek dayOfWeek;

        public PreferenceResponse(Preference preference) {
            this.id = preference.getId();
            this.startAt = preference.getStartAt();
            this.endAt = preference.getEndAt();
            this.regionId = preference.getRegion().getId();
            this.dayOfWeek = preference.getDayOfWeek();
        }
    }

    @Data
    static class PreferenceInRegionResponse {
        private Long id;
        private String name;
        private String depth1;
        private String depth2;
        private String depth3;
        private List<PreferenceResponse> preferences;

        public PreferenceInRegionResponse(Region region, List<Preference> preferences) {
            this.id = region.getId();
            this.name = region.getName();
            this.depth1 = region.getDepth1();
            this.depth2 = region.getDepth2();
            this.depth3 = region.getDepth3();
            List<PreferenceResponse> preferenceResponses = new ArrayList<>();
            for (Preference preference : preferences) {
                preferenceResponses.add(new PreferenceResponse(preference));
            }
            this.preferences = preferenceResponses;
        }
    }
}
