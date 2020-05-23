package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> b893e3fba514fb9112a3ea47045057d6e33e3620
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUser(String email){
        return userRepository.findByEmail(email);
    }
<<<<<<< HEAD

    public Optional<User> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        return userRepository.findByEmail(email);
    }
=======
>>>>>>> b893e3fba514fb9112a3ea47045057d6e33e3620
}
