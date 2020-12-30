package com.wafflestudio.draft.model

import org.locationtech.jts.geom.Point
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "region_id"])])
class Court(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(optional = false, fetch = FetchType.LAZY)
        @JoinColumn(name = "region_id", referencedColumnName = "id")
        var region: Region? = null,

        var name: String? = null,

        @field:Min(value = 0, message = "The value must be positive.")
        var capacity: Int? = null,

        @Column(nullable = false, columnDefinition = "Geometry(Point,4326)")
        var location: Point? = null,

        @OneToMany(mappedBy = "court", fetch = FetchType.LAZY)
        var rooms: MutableList<Room> = mutableListOf()
)
