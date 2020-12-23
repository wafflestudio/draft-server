package com.wafflestudio.draft.dto

import com.wafflestudio.draft.dto.response.RoomResponse

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
    ) {
        constructor(regionInfo: Info) : this(
                regionInfo.id,
                regionInfo.name,
                regionInfo.depth1,
                regionInfo.depth2,
                regionInfo.depth3
        )
    }

    data class Info(
            val id: Long?,
            val name: String?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?
    ) {
        fun toResponse(): Response{
            return Response(this)
        }
    }

    data class WithRooms(
            val id: Long?,
            val name: String?,
            val depth1: String?,
            val depth2: String?,
            val depth3: String?,
            var rooms: MutableList<RoomResponse>?
    )

}
