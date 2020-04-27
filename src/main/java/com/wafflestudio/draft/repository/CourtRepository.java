package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court, Long> {
}
