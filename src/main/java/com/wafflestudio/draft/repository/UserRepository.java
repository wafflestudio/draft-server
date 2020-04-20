package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
