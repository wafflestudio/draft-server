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
    private String roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;
}
