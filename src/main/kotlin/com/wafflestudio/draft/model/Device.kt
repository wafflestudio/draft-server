package com.wafflestudio.draft.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Device(
        @field:NotBlank
        @Column(unique = true)
        var deviceToken: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        var user: User? = null
) : BaseTimeEntity()
