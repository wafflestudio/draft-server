package com.wafflestudio.draft.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"rooms", "region"})
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NonNull
    @Column(unique = true)
    private String username;

    @NotBlank
    @NonNull
    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String profileImage = "https://lh3.googleusercontent.com/-XdUIqdMkCWA/" +
                                    "AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg";

    @Column
    private String roles;

    @OneToMany(mappedBy = "owner")
    private List<Room> rooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Device> devices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    public void addRole(String role) {
        roles = roles + "," + role;
    }
}
