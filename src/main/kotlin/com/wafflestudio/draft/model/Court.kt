package com.wafflestudio.draft.model

import com.vividsolutions.jts.geom.Point
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name", "region_id"])])
data class Court(
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     var id: Long? = null,

     @ManyToOne
     @JoinColumn(name = "region_id", referencedColumnName = "id")
     var region: Region? = null,
     var name: String? = null,

     @field:Min(value = 0, message = "The value must be positive.")
     var capacity: Int? = null,
     var location: Point? = null,

     @OneToMany(mappedBy = "court")
     var rooms: List<Room>? = null
) {}
