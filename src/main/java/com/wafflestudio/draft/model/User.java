package com.wafflestudio.draft.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Room> rooms;
}
