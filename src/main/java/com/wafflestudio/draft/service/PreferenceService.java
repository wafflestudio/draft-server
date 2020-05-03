package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public void setPreference(User user, Preference preference) {
        preferenceRepository.deleteAllByUser(user);
        preference.setUser(user);
        preferenceRepository.save(preference);
    }
}
