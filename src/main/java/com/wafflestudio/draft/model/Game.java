package com.wafflestudio.draft.model;

import com.wafflestudio.draft.model.enums.GameResult;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class Game extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value=0, message = "The value must be positive.")
    private Integer elapsedTime;

    // TODO: How can we store results and scores of Game well?

    // ex > "3:5" (cf > score of UserGameLog)
    private String gameScore;
}
