package com.wafflestudio.draft.model;

import javax.persistence.*;
import java.util.HashSet;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column(unique = true)
    private String email;
}
