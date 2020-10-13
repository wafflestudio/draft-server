package com.wafflestudio.draft.model

import com.wafflestudio.draft.model.enums.Team
import javax.persistence.*

@Entity
data class Participant(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        var user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id", referencedColumnName = "id")
        var room: Room,
        var team: Team = Team.A,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
) : BaseTimeEntity()
