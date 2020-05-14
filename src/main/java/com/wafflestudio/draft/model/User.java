package com.wafflestudio.draft.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null) return new ArrayList<>();

        List<String> roles = Arrays.asList(this.roles.split(","));
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
