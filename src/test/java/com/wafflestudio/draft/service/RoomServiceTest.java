package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Court;
import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.CourtRepository;
import com.wafflestudio.draft.repository.RoomRepository;
import com.wafflestudio.draft.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        User user = new User("TEST_USER", "user@test.com", null, null, null, null, null, null, null);
        userRepository.save(user);
        room.setOwner(user);

        Court court = new Court();
        courtRepository.save(court);
        room.setCourt(court);

        // when
        Long savedId = roomService.save(room);

        // then
        assertEquals(room, roomRepository.findOne(savedId));
    }
}