package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Region

class RoomInRegionResponse(
        var id: Long? = null,
        var name: String? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        var rooms: List<RoomResponse>? = null
) {
    constructor(region: Region): this() {
        this.id = region.id
        this.name = region.name
        this.depth1 = region.depth1
        this.depth2 = region.depth2
        this.depth3 = region.depth3
        val roomResponses: MutableList<RoomResponse> = mutableListOf()
        region.courts.map { court ->
            court.rooms.map { roomResponses.add(RoomResponse(it)) }
        }
        this.rooms = roomResponses;
    }
}
