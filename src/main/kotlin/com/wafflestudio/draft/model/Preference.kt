package com.wafflestudio.draft.model

import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.*

@Entity
class Preference(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        var user: User? = null,

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "region_id", referencedColumnName = "id")
        var region: Region? = null,
        var startAt: LocalTime? = null,
        var endAt: LocalTime? = null,

        @Column(nullable = false)
        @Enumerated(EnumType.ORDINAL)
        var dayOfWeek: DayOfWeek? = null

) : BaseTimeEntity()
