package com.wafflestudio.draft.dto

import com.wafflestudio.draft.dto.response.Location
import com.wafflestudio.draft.model.Court

class CourtDTO {
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
            court.location?.let {
                Location(it)
            },
            court.rooms.map {
                it.toResponse()
            }
        )
    }

    data class GetCourtsRequest(
        val name: String? = null
    )
}