package com.wafflestudio.draft.service

import com.google.firebase.messaging.FirebaseMessaging
import com.wafflestudio.draft.model.Device
import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.model.User
import com.wafflestudio.draft.repository.PreferenceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalTime

@Service
@Transactional
class PreferenceService {
    @Autowired
    private val preferenceRepository: PreferenceRepository? = null
    fun setPreferences(user: User, region: Region, preferences: List<Preference>?) {
        preferenceRepository!!.deleteAllByUser(user)
        // TODO: Add unsubscribing logic
        val registrationTokens: List<String> = user.devices!!.map { obj: Device? -> obj!!.deviceToken }
        FirebaseMessaging.getInstance().subscribeToTopicAsync(registrationTokens, region.name)
        for (preference in preferences!!) {
            preference.region = region
            preference.user = user
            preferenceRepository.save<Preference>(preference)
        }
    }

    fun getPlayableUsers(region: String?, dayOfWeek: DayOfWeek?, start: LocalTime?, end: LocalTime?): List<Long?>? {
        return preferenceRepository!!.getPlayableUsers(region, dayOfWeek, start, end)
    }
}
