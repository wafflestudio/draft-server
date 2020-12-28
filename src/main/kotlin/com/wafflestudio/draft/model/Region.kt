package com.wafflestudio.draft.model

import com.wafflestudio.draft.dto.RegionDTO
import org.locationtech.jts.geom.MultiPolygon
import javax.persistence.*

@Entity
class Region(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var emdCode: Long? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,

        @Column(unique = true)
        var name: String? = null,

        @Column(nullable = false, columnDefinition = "Geometry(MultiPolygon,5179)")
        var polygon: MultiPolygon? = null,

        @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var users: MutableList<User> = mutableListOf(),

        @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var courts: MutableList<Court> = mutableListOf(),

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "region")
        var rooms: MutableList<Room> = mutableListOf()

) {
    constructor(id: Long?, emdCode: Long?, depth1: String?, depth2: String?, depth3: String?, name: String?)
            : this(id, emdCode, depth1, depth2, depth3, name, null)

    fun toResponse(): RegionDTO.Response {
        return RegionDTO.Response(id, name, depth1, depth2, depth3)
    }

    fun toResponseWithRooms(): RegionDTO.ResponseWithRooms {
        println(this.courts)
        return RegionDTO.ResponseWithRooms(this)
    }
}