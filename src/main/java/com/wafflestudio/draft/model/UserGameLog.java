package com.wafflestudio.draft.model;

import com.wafflestudio.draft.model.enums.GameResult;
import com.wafflestudio.draft.model.enums.Team;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class UserGameLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private GameResult result;

    // the score of the belonging team
    @Min(value=0, message = "The value must be positive.")
    private Integer score;

    @Enumerated(EnumType.STRING)
    private Team team;
}
