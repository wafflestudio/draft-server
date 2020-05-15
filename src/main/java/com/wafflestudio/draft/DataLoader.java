package com.wafflestudio.draft;

import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.RegionRepository;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    UserRepository userRepository;
    RegionRepository regionRepository;

    public DataLoader(UserRepository userRepository, RegionRepository regionRepository) {
        this.userRepository = userRepository;
        this.regionRepository = regionRepository;
    }


    @Override
    public void run(ApplicationArguments args) {
        User oauth2User = new User("OAUTH2_TESTUSER", "authuser@test.com");
        oauth2User.setRoles("TEST_API");
        userRepository.save(oauth2User);

        User passwordUser = new User("PASSWORD_TESTUSER", "passworduser@test.com");
        passwordUser.setRoles("TEST_API");
        passwordUser.setPassword(new BCryptPasswordEncoder().encode("testpassword"));
        userRepository.save(passwordUser);
    }
}
