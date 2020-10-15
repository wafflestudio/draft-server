package com.wafflestudio.draft.model

import com.wafflestudio.draft.model.enums.RoomStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Room(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(nullable = false, columnDefinition = "varchar(32) default 'waiting'")
        @Enumerated(EnumType.STRING)
        var status: RoomStatus = RoomStatus.WAITING,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "owner_id", referencedColumnName = "id")
        var owner: User? = null,
        var startTime: LocalDateTime? = null,
        var endTime: LocalDateTime? = null,
        var name: String? = null,

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "court_id", referencedColumnName = "id")
        var court: Court? = null,

        @OneToMany(mappedBy = "room")
        var participants: List<Participant>? = null
) : BaseTimeEntity()
