package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    void deleteAllByUser(User user);
    //TODO: use other method with better performance
}
