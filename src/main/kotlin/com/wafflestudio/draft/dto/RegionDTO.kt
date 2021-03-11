package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.Region

class RegionDTO {
    data class Request(
            val depth3: String?,
            val page: Int = 0
    )

    data class Response(
            val id: Long?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?,
            val name: String?
    )

    data class Summary(
            val id: Long?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?,
            val name: String?
    ) {
        fun toResponse(): Response {
            return Response(id, depth1, depth2, depth3, name)
        }
    }

    data class SummaryWithRooms(
            val id: Long? = null,
            val depth1: String? = null,
            val depth2: String? = null,
            val depth3: String? = null,
            val name: String? = null,
            var rooms: MutableMap<Long, RoomDTO.Summary> = mutableMapOf()
    ) {
        fun toResponseWithRooms(): ResponseWithRooms {
            return ResponseWithRooms(id, depth1, depth2, depth3, name, rooms.values.map { it.toResponse() })
        }
    }

    data class ResponseWithRooms(
            val id: Long?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?,
            val name: String?,
            var rooms: List<RoomDTO.Response>
    ) {
        constructor(region: Region) : this(
                region.id,
                region.depth1,
                region.depth2,
                region.depth3,
                region.name,
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
