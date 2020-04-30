package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
