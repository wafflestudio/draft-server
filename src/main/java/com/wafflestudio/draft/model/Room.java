package com.wafflestudio.draft.model;

import com.wafflestudio.draft.model.enums.RoomStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    private LocalDateTime startTime;

    // TODO: court_id

}
