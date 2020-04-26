package com.wafflestudio.draft.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer startAt;

    @Column
    private Integer endAt;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "region_id",referencedColumnName = "region_id")
    private Region region;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
}
