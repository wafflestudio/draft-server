package com.wafflestudio.draft.model

import javax.persistence.*

@Entity
data class User(
        @Column(unique = true)
        var username: String,

        @Column(unique = true)
        var email: String,

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
        var rooms: List<Room>? = null,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        var devices: List<Device?>? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "region_id", referencedColumnName = "id")
        var region: Region? = null
) : BaseTimeEntity() {

    fun addRole(role: String) {
        roles = "$roles,$role"
    }
}
