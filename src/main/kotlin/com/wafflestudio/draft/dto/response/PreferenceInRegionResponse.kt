package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.Region
import java.util.*

class PreferenceInRegionResponse(
        var id: Long? = null,
        var name: String? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        var preferences: List<PreferenceResponse>? = null
) {
    constructor(region: Region, preferences: List<Preference?>?) {
        id = region.id
        name = region.name
        depth1 = region.depth1
        depth2 = region.depth2
        depth3 = region.depth3
        val preferenceResponses: MutableList<PreferenceResponse> = ArrayList()
        for (preference in preferences!!) {
            preferenceResponses.add(PreferenceResponse(preference))
        }
        this.preferences = preferenceResponses
    }
}
