package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Court;
import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.CourtRepository;
import com.wafflestudio.draft.repository.RoomRepository;
import com.wafflestudio.draft.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class RoomServiceTest {

    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourtRepository courtRepository;

    @Test
    void create() throws Exception {
        // given
        Room room = new Room();
        User user = new User();
        user.setUsername("TEST_USER");
        user.setEmail("user@test.com");
        userRepository.save(user);
        room.setOwner(user);

        GeometryFactory gf = new GeometryFactory();
        Point location = gf.createPoint(new Coordinate(37.5186202, 126.904905));
        location.setSRID(4326);
        Court court = new Court();
        court.setLocation(location);
        courtRepository.save(court);
        room.setCourt(court);

        // when
        Long savedId = roomService.save(room);

        // then
        assertEquals(room, roomRepository.findById(savedId).get());
    }
}
