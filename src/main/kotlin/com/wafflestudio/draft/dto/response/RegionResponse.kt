package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Region

data class RegionResponse(
    var id: Long? = null,
    var name: String? =null,
    var depth1: String? = null,
    var depth2: String? = null,
    var depth3: String? = null
)
{
    constructor(region:Region){
        id = region.id
        name = region.name
        depth1 = region.depth1
        depth2 = region.depth2
        depth3 = region.depth3
    }
}