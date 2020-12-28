package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.Region

class RegionDTO {
    data class Request(
            val depth3: String?
    )

    data class Response(
            val id: Long?,
            val name: String?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?
    )

    data class SummaryWithRooms(
            val id: Long? = null,
            val name: String? = null,
            val depth1: String? = null,
            val depth2: String? = null,
            val depth3: String? = null,
            var rooms: MutableMap<Long, RoomDTO.Summary> = mutableMapOf()
    ) {
        fun toResponseWithRooms(): ResponseWithRooms {
            return ResponseWithRooms(id, name, depth1, depth2, depth3, rooms.values.map { it.toResponse() })
        }
    }

    data class ResponseWithRooms(
            val id: Long?,
            val name: String?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?,
            var rooms: List<RoomDTO.Response>
    ) {
        constructor(region: Region) : this(
                region.id,
                region.name,
                region.depth1,
                region.depth2,
                region.depth3,
                listOf<RoomDTO.Response>()
        ) {
            val newRooms: MutableList<RoomDTO.Response> = mutableListOf()
            region.courts.forEach { court ->
                court.rooms.forEach { newRooms.add(it.toResponse()) }
            }
            this.rooms = newRooms
        }
    }
}
