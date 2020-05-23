package com.wafflestudio.draft.model;

import lombok.*;

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
    private String roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Device> devices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    public void addRole(String role) {
        roles = roles + "," + role;
    }

}
