package com.wafflestudio.draft.model;

import com.vividsolutions.jts.geom.Point;
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
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id",referencedColumnName = "region_id")
    private Region region;

    @Column
    private String name;

    @Column
    private Integer capacity;

    @Column
    private Point location;
}
