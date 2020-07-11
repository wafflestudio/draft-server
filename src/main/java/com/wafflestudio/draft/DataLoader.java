package com.wafflestudio.draft;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.wafflestudio.draft.model.Court;
import com.wafflestudio.draft.model.Device;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.CourtRepository;
import com.wafflestudio.draft.repository.DeviceRepository;
import com.wafflestudio.draft.repository.RegionRepository;
import com.wafflestudio.draft.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    final UserRepository userRepository;
    final RegionRepository regionRepository;
    final CourtRepository courtRepository;
    final DeviceRepository deviceRepository;
    final GeometryFactory gf = new GeometryFactory();

    @Override
    public void run(ApplicationArguments args) {
        Point point = gf.createPoint(new Coordinate(2, 5));

        Region testRegion = new Region();
        testRegion.setName("TEST_REGION");
        regionRepository.save(testRegion);

        Court testCourt = new Court();
        testCourt.setName("TEST_COURT");
        testCourt.setCapacity(10);
        testCourt.setRegion(testRegion);
        // FIXME: how to set location with Point?
        // testCourt.setLocation(point);
        courtRepository.save(testCourt);

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

        Device testDevice = new Device("euSJRfcqJTc:APA91bFIUxmYZX68KWUZSZPW0sMhCl1tJKdH8L-lvhUv71DbePYmA8RI-QrVGGAqBzoxfklsl-i7NdgazQAHGQXlFkCnaCIpP3B_oDCCkpTR_HxxUVeNoG8_DeNODrwxEMfardoz_4Ym");
        testDevice.setUser(passwordUser);
        deviceRepository.save(testDevice);
    }
}
