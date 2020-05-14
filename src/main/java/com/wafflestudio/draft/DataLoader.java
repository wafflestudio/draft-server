package com.wafflestudio.draft;

import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.RegionRepository;
import com.wafflestudio.draft.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
        User testuser = new User("TEST", "testuser@test.com");
        testuser.setRoles("TEST_API");
        userRepository.save(testuser);
    }
}
