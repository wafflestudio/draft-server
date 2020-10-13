package com.wafflestudio.draft.model

import java.time.DayOfWeek
import java.time.LocalTime
import javax.persistence.*

@Entity
class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private var user: User? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private var region: Region? = null
    private var startAt: LocalTime? = null
    private var endAt: LocalTime? = null

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private var dayOfWeek: DayOfWeek? = null

    constructor(id: Long?, user: User?, region: Region?, startAt: LocalTime?, endAt: LocalTime?, dayOfWeek: DayOfWeek?) {
        this.id = id
        this.user = user
        this.region = region
        this.startAt = startAt
        this.endAt = endAt
        this.dayOfWeek = dayOfWeek
    }

    constructor() {}

    fun getId(): Long? {
        return id
    }

    fun getUser(): User? {
        return user
    }

    fun getRegion(): Region? {
        return region
    }

    fun getStartAt(): LocalTime? {
        return startAt
    }

    fun getEndAt(): LocalTime? {
        return endAt
    }

    fun getDayOfWeek(): DayOfWeek? {
        return dayOfWeek
    }

    fun setId(id: Long?) {
        this.id = id
    }

    fun setUser(user: User?) {
        this.user = user
    }

    fun setRegion(region: Region?) {
        this.region = region
    }

    fun setStartAt(startAt: LocalTime?) {
        this.startAt = startAt
    }

    fun setEndAt(endAt: LocalTime?) {
        this.endAt = endAt
    }

    fun setDayOfWeek(dayOfWeek: DayOfWeek?) {
        this.dayOfWeek = dayOfWeek
    }
}