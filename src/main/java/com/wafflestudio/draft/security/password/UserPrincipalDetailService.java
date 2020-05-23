package com.wafflestudio.draft.security.password;

import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '%s' not found"));

        return new UserPrincipal(user);
    }


}
