package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public void setPreferences(User user, Region region, List<Preference> preferences) {
        preferenceRepository.deleteAllByUser(user);
        for (Preference preference: preferences) {
            preference.setRegion(region);
            preference.setUser(user);
            preferenceRepository.save(preference);
        }
    }
}
