package com.wafflestudio.draft.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Device extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank
    @Column(unique = true)
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
