package com.wafflestudio.draft.model

import com.wafflestudio.draft.model.enums.GameResult
import com.wafflestudio.draft.model.enums.Team
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class UserGameLog : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private val user: User? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private val game: Game? = null

    @Enumerated(EnumType.STRING)
    private val result: GameResult? = null

    // the score of the belonging team
    @Min(value = 0, message = "The value must be positive.")
    private val score: Int? = null

    @Enumerated(EnumType.STRING)
    private val team: Team? = null
}
