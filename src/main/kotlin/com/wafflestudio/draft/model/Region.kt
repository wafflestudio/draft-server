package com.wafflestudio.draft.model

import com.vividsolutions.jts.geom.Geometry
import javax.persistence.*

@Entity
data class Region(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var emdCode: Long? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        @Column(nullable=false,columnDefinition="Geometry(MultiPolygon,5179)")
        var polygon: Geometry,

        @Column(unique = true)
        var name: String? = null,

        @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL])
        var users: MutableList<User> = mutableListOf(),

        @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL])
        var courts: MutableList<Court> = mutableListOf()
) : BaseTimeEntity()
