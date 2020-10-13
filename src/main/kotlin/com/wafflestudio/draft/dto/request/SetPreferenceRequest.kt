package com.wafflestudio.draft.dto.request

import com.wafflestudio.draft.model.Preference

data class SetPreferenceRequest(
        var regionId: Long? = null,
        var preferences: List<Preference>? = null
)
