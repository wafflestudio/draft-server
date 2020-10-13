package com.wafflestudio.draft.model

import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Room : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false, columnDefinition = "varchar(32) default 'waiting'")
    @Enumerated(EnumType.STRING)
    private var status = RoomStatus.WAITING

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private var owner: User? = null
    private var startTime: LocalDateTime? = null
    private var endTime: LocalDateTime? = null
    private var name: String? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", referencedColumnName = "id")
    private var court: Court? = null
    fun getId(): Long? {
        return id
    }

    fun getStatus(): RoomStatus {
        return status
    }

    fun getOwner(): User? {
        return owner
    }

    fun getStartTime(): LocalDateTime? {
        return startTime
    }

    fun getEndTime(): LocalDateTime? {
        return endTime
    }

    fun getName(): String? {
        return name
    }

    fun getCourt(): Court? {
        return court
    }

    fun setId(id: Long?) {
        this.id = id
    }

    fun setStatus(status: RoomStatus) {
        this.status = status
    }

    fun setOwner(owner: User?) {
        this.owner = owner
    }

    fun setStartTime(startTime: LocalDateTime?) {
        this.startTime = startTime
    }

    fun setEndTime(endTime: LocalDateTime?) {
        this.endTime = endTime
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun setCourt(court: Court?) {
        this.court = court
    }
}