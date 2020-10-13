package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.Region

class PreferenceInRegionResponse(
        var id: Long? = null,
        var name: String? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        var preferences: List<PreferenceResponse>? = null
) {
    constructor(region: Region, preferences: List<Preference?>?) : this(
            region.id,
            region.name,
            region.depth1,
            region.depth2,
            region.depth3,
            preferences?.map { PreferenceResponse(it) }
    )
}
