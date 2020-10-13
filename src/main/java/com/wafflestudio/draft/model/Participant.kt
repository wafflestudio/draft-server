package com.wafflestudio.draft.model

import javax.persistence.*

@Entity
class Participant : BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private var user: User = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private var room: Room = null
    private var team = 0

    constructor(user: User, room: Room, team: Int) {
        this.user = user
        this.room = room
        this.team = team
    }

    constructor() {}

    fun getId(): Long? {
        return id
    }

    fun getUser(): User {
        return user
    }

    fun getRoom(): Room {
        return room
    }

    fun getTeam(): Int {
        return team
    }

    fun setId(id: Long?) {
        this.id = id
    }

    fun setUser(user: User) {
        this.user = user
    }

    fun setRoom(room: Room) {
        this.room = room
    }

    fun setTeam(team: Int) {
        this.team = team
    }
}