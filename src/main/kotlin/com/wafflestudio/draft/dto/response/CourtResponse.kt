package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.dto.RoomDTO
import com.wafflestudio.draft.model.Court

data class CourtResponse(
        var id: Long? = null,
        var regionId: Long? = null,
        var name: String? = null,
        var capacity: Int? = null,
        var location: Location? = null,
        var rooms: List<RoomDTO.Response>
) {
    constructor(court: Court) : this(
            court.id,
            court.region!!.id,
            court.name,
            court.capacity,
            Location(court.location),
            court.rooms.map { it.toResponse() }
    )
}
