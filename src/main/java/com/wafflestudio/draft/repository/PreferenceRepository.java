package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    //TODO: use other method for better performance
    void deleteAllByUser(User user);


    @Query("SELECT DISTINCT u.id,u.username,u. from Preference p " +
            "INNER JOIN p.region r " +
            "INNER JOIN p.user u " +
            "WHERE r.name = :region " +
            "AND p.dayOfWeek = :day_of_week " +
            "AND p.startAt <= :start_time " +
            "AND p.endAt >= :end_time")
    List<Long> getUsersApproachable(
            @Param("region") String region,
            @Param("day_of_week") DayOfWeek dayOfWeek,
            @Param("start_time") LocalTime start,
            @Param("end_time") LocalTime end);
}
