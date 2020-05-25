package com.wafflestudio.draft.api;


import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.request.SignUpRequest;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.security.oauth2.AuthUserService;
import com.wafflestudio.draft.security.oauth2.OAuth2Provider;
import com.wafflestudio.draft.security.oauth2.client.OAuth2Response;
import com.wafflestudio.draft.security.password.UserPrincipal;
import com.wafflestudio.draft.service.PreferenceService;
import com.wafflestudio.draft.service.RegionService;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final OAuth2Provider oAuth2Provider;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;
    private final RegionService regionService;
    private final PreferenceService preferenceService;

    public UserApiController(OAuth2Provider oAuth2Provider, AuthUserService authUserService, PasswordEncoder passwordEncoder, RegionService regionService, PreferenceService preferenceService) {
        this.oAuth2Provider = oAuth2Provider;
        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder;
        this.regionService = regionService;
        this.preferenceService = preferenceService;
    }

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
    public String myInfo(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser);
        return currentUser.getEmail();
    }

    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/info/")
    public void setPreferences(@Valid @RequestBody List<SetPreferenceRequest> preferenceRequestList, @CurrentUser UserPrincipal currentUser) {

        for (SetPreferenceRequest preferenceRequest : preferenceRequestList) {
            Region duplicatedRegion = regionService.getRegionByName(preferenceRequest.getRegionName());
            preferenceService.setPreferences(currentUser, duplicatedRegion, preferenceRequest.preferences);
        }
    }

    @GetMapping("/pref/")
    public List<Long> getUsersByPreference(@Valid @ModelAttribute GetUsersByPreferenceRequest getUsersByPreferenceRequest) {
        String regionName = getUsersByPreferenceRequest.getRegionName();
        DayOfWeek dayOfWeek = getUsersByPreferenceRequest.getDayOfWeek();
        LocalTime startTime = getUsersByPreferenceRequest.getStartTime();
        LocalTime endTime = getUsersByPreferenceRequest.getEndTime();
        return preferenceService.getUsersApproachable(regionName, dayOfWeek, startTime, endTime);
    }

    @Data
    static class SetPreferenceRequest {
        private String regionName;
        private List<Preference> preferences;
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
}
