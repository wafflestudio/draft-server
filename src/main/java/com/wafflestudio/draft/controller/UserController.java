package com.wafflestudio.draft.controller;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.service.PreferenceService;
import com.wafflestudio.draft.service.RegionService;
import com.wafflestudio.draft.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/user/")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    RegionService regionService;

    @PostMapping(path = "info/")
    public void setPreferences(@Valid @RequestBody List<PreferenceRequest> preferenceRequests) {
//        User currentUser = userService.currentUser().orElseThrow(() -> {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        });
        // FIXME: This codes below should be changed to above ones
        User currentUser = userService.findUser("testuser@test.com").orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        });

        for (PreferenceRequest preferenceRequest : preferenceRequests) {
            Region duplicatedRegion = regionService.getRegionByName(preferenceRequest.getRegionName());
            preferenceService.setPreferences(currentUser, duplicatedRegion, preferenceRequest.preferences);
        }
    }

    @Data
    static class PreferenceRequest {
        private String regionName;
        private List<Preference> preferences;
    }
}
