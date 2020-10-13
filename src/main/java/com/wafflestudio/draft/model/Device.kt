package com.wafflestudio.draft.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Device : BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @NotBlank
    @Column(unique = true)
    private var deviceToken: String = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private var user: User? = null

    constructor(deviceToken: String) {
        this.deviceToken = deviceToken
    }

    constructor() {}

    fun getId(): Long? {
        return id
    }

    fun getDeviceToken(): String {
        return deviceToken
    }

    fun getUser(): User? {
        return user
    }

    fun setId(id: Long?) {
        this.id = id
    }

    fun setDeviceToken(deviceToken: String) {
        this.deviceToken = deviceToken
    }

    fun setUser(user: User?) {
        this.user = user
    }
}