package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
