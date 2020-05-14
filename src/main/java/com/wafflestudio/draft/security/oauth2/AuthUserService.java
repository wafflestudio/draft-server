package com.wafflestudio.draft.security.oauth2;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<User> loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkUserExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public User saveUser(User user) { return userRepository.save(user); }
}
