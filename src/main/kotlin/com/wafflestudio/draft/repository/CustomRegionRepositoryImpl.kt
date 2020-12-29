package com.wafflestudio.draft.repository

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.dto.RoomDTO
import com.wafflestudio.draft.dto.response.UserInformationResponse
import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime
import javax.persistence.EntityManager

class CustomRegionRepositoryImpl(private val entityManager: EntityManager) : CustomRegionRepository {
    override fun findAllRegionWithoutGeometryData(): List<RegionDTO.SummaryWithRooms> {
        val results: List<Any?> = entityManager.createQuery("""
            SELECT rg.id AS rg_id,
                   rg.depth1 AS rg_depth1,
                   rg.depth2 AS rg_depth2,
                   rg.depth3 AS rg_depth3,
                   rg.name AS rg_name,
                   rm.id AS rm_id,
                   rm.status AS rm_status,
                   rm.startTime AS rm_start_time,
                   rm.endTime AS rm_end_time,
                   rm.name AS rm_name,
                   rm.createdAt AS rm_created_at,
                   rm.owner.id AS rm_owner_id,
                   rm.court.id AS rm_court_id,
                   u.id AS u_id,
                   u.username AS u_username,
                   u.email AS u_email,
                   u.profileImage AS u_profile_image
            FROM Region rg
            LEFT JOIN rg.courts c
            LEFT JOIN c.rooms rm
            LEFT JOIN rm.participants p
            LEFT JOIN p.user u
            """
        ).resultList

        val regions: MutableMap<Long, RegionDTO.SummaryWithRooms> = mutableMapOf()

        results.forEach { row ->
            val column: Array<*> = (row as Array<*>)
            val regionId: Long = column[0] as Long
            val roomId: Long? = column[5] as Long?
            val userId: Long? = column[13] as Long?
            val region = regions.getOrPut(regionId, {
                RegionDTO.SummaryWithRooms(
                        column[0] as Long?,
                        column[1] as String?,
                        column[2] as String?,
                        column[3] as String?,
                        column[4] as String?
                )
            })
            if (roomId != null) {
                val room = region.rooms.getOrPut(roomId, {
                    RoomDTO.Summary(
                            column[5] as Long?,
                            column[6] as RoomStatus?,
                            column[7] as LocalDateTime?,
                            column[8] as LocalDateTime?,
                            column[9] as String?,
                            column[10] as LocalDateTime?,
                            column[11] as Long?,
                            column[12] as Long?
                    )
                })
                if (userId != null) {
                    room.participants.putIfAbsent(userId, UserInformationResponse(
                            column[13] as Long,
                            column[14] as String,
                            column[15] as String,
                            column[16] as String
                    ))
                }
            }
        }
        return regions.values.toList()
    }
}