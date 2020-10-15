package com.wafflestudio.draft.model

import com.vividsolutions.jts.geom.Polygon
import javax.persistence.*

@Entity
data class Region(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var depth1: String? = null,
        var depth2: String? = null,
        var depth3: String? = null,
        var polygon: Polygon? = null,

        @Column(unique = true)
        var name: String? = null,

        @OneToMany(mappedBy = "region", cascade = [CascadeType.ALL])
        var users: MutableList<User> = mutableListOf()
) : BaseTimeEntity()
