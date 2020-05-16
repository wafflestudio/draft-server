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
        Region testRegion = new Region();
        testRegion.setName("TEST_REGION");
        regionRepository.save(testRegion);

        User oauth2User = new User("OAUTH2_TESTUSER", "authuser@test.com");
        oauth2User.addRole("TEST_API");
        oauth2User.addRole("ROLE_USER");
        oauth2User.addRole("USER");
        oauth2User.setRegion(testRegion);
        userRepository.save(oauth2User);

        User passwordUser = new User("PASSWORD_TESTUSER", "passworduser@test.com");
        passwordUser.addRole("TEST_API");
        passwordUser.addRole("ROLE_USER");
        passwordUser.addRole("USER");
        passwordUser.setRegion(testRegion);
        passwordUser.setPassword(new BCryptPasswordEncoder().encode("testpassword"));
        userRepository.save(passwordUser);

    }
}
