package com.wafflestudio.draft.model;

import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "region_id"})})
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    private String name;

    @Min(value = 0, message = "The value must be positive.")
    private Integer capacity;

    private Point location;

    @OneToMany(mappedBy = "court")
    private List<Room> rooms;
}
