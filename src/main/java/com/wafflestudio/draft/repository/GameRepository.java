package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
