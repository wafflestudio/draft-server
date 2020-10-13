package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.DayOfWeek
import java.time.LocalTime

@Repository
interface PreferenceRepository : JpaRepository<Preference?, Long?> {
    //TODO: use other method for better performance
    fun deleteAllByUser(user: User?)

    @Query("SELECT DISTINCT u.id from Preference p " +
            "INNER JOIN p.region r " +
            "INNER JOIN p.user u " +
            "WHERE r.name = :region " +
            "AND p.dayOfWeek = :day_of_week " +
            "AND p.startAt <= :start_time " +
            "AND p.endAt >= :end_time")
    fun getPlayableUsers(
            @Param("region") region: String?,
            @Param("day_of_week") dayOfWeek: DayOfWeek?,
            @Param("start_time") start: LocalTime?,
            @Param("end_time") end: LocalTime?): List<Long?>?
}