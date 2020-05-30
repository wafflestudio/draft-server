package com.wafflestudio.draft.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.wafflestudio.draft.model.Device;
import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    public void setPreferences(User user, Region region, List<Preference> preferences) {
        preferenceRepository.deleteAllByUser(user);
        // TODO: Add unsubscribing logic

        List<String> registrationTokens = user.getDevices().stream()
                .map(Device::getDeviceToken)
                .collect(Collectors.toList());
        FirebaseMessaging.getInstance().subscribeToTopicAsync(registrationTokens, region.getName());
        for (Preference preference : preferences) {
            preference.setRegion(region);
            preference.setUser(user);
            preferenceRepository.save(preference);
        }
    }

    public List<Long> getPlayableUsers(String region, DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        return preferenceRepository.getPlayableUsers(region, dayOfWeek, start, end);
    }
}
