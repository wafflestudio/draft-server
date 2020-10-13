package com.wafflestudio.draft.model

import javax.persistence.*

@Entity
data class User(

        @Column(unique = true)
        var username: String,

        @Column(unique = true)
        var email: String

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column
    private var password: String? = null

    @Column
    private var roles: String? = null

    @OneToMany(mappedBy = "owner")
    private var rooms: List<Room>? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private var devices: List<Device?>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private var region: Region? = null

    constructor() {}

    fun addRole(role: String) {
        roles = "$roles,$role"
    }

    fun getId(): Long? {
        return id
    }

    open fun getPassword(): String {
        return password!!
    }

    fun getRoles(): String? {
        return roles
    }

    fun getRooms(): List<Room>? {
        return rooms
    }

    fun getDevices(): List<Device?>? {
        return devices
    }

    fun getRegion(): Region? {
        return region
    }

    fun setId(id: Long?) {
        this.id = id
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun setRoles(roles: String?) {
        this.roles = roles
    }

    fun setRooms(rooms: List<Room>?) {
        this.rooms = rooms
    }

    fun setDevices(devices: List<Device?>?) {
        this.devices = devices
    }

    fun setRegion(region: Region?) {
        this.region = region
    }

}