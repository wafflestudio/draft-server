package com.wafflestudio.draft.model;

import com.wafflestudio.draft.model.enums.RoomStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(32) default 'waiting'")
    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.WAITING;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    private LocalDateTime startTime;

    // TODO: court_id
}