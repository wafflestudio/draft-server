package com.wafflestudio.draft.model;

import com.vividsolutions.jts.geom.Polygon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String depth1;

    @Column
    private String depth2;

    @Column
    private String depth3;

    @Column
    private Polygon polygon;

    @Column
    private String name;
}
