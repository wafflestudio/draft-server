package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
