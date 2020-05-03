package com.wafflestudio.draft.controller;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.service.PreferenceService;
import com.wafflestudio.draft.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping("api/user/")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PreferenceService preferenceService;

    @PostMapping(path = "info/")
    public ResponseEntity<Preference> setPreference(@Valid @NotNull @RequestBody Preference preference) {
        User currentUser = userService.currentUser().orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        });
        preferenceService.setPreference(currentUser, preference);
        return new ResponseEntity<>(preference, HttpStatus.CREATED);
    }
}
