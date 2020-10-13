package com.wafflestudio.draft.model

import com.vividsolutions.jts.geom.Polygon
import javax.persistence.*

@Entity
data class Region {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var depth1: String? = null
    var depth2: String? = null
    var depth3: String? = null
    var polygon: Polygon? = null

    @Column(unique = true)
    var name: String? = null

    @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL])
    var users: List<User>? = null

    constructor(id: Long?, depth1: String?, depth2: String?, depth3: String?, polygon: Polygon?, name: String?, users: List<User>?) {
        this.id = id
        this.depth1 = depth1
        this.depth2 = depth2
        this.depth3 = depth3
        this.polygon = polygon
        this.name = name
        this.users = users
    }

    constructor() {}

    fun setId(id: Long?) {
        this.id = id
    }

    fun setDepth1(depth1: String?) {
        this.depth1 = depth1
    }

    fun setDepth2(depth2: String?) {
        this.depth2 = depth2
    }

    fun setDepth3(depth3: String?) {
        this.depth3 = depth3
    }

    fun setPolygon(polygon: Polygon?) {
        this.polygon = polygon
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun setUsers(users: List<User>?) {
        this.users = users
    }
}