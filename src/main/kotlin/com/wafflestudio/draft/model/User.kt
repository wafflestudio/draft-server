package com.wafflestudio.draft.model

import javax.persistence.*

@Entity
@Table(name = "draft_user")
class User(
        @Column(unique = true)
        var username: String = "",

        @Column(unique = true)
        var email: String = "",

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column
        var password: String? = null,

        @Column
        var profileImage: String? = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/" +
                "AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg",

        @Column
        var roles: String? = null,

        @OneToMany(mappedBy = "owner")
        var rooms: MutableList<Room> = mutableListOf(),

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        var devices: MutableList<Device> = mutableListOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "region_id", referencedColumnName = "id")
        var region: Region? = null
) : BaseTimeEntity() {

    fun addRole(role: String) {
        roles = "$roles,$role"
    }
}
