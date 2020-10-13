package com.wafflestudio.draft.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Min

@Entity
class Game : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Min(value = 0, message = "The value must be positive.")
    private val elapsedTime: Int? = null

    // TODO: How can we store results and scores of Game well?
    // ex > "3:5" (cf > score of UserGameLog)
    private val gameScore: String? = null
}