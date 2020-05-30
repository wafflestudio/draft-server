package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class RoomServiceTest {

    @Autowired RoomService roomService;
    @Autowired UserService userService;
    @Autowired RoomRepository roomRepository;

    @Test
    void create() throws Exception {
        // given
        Room room = new Room();
        User user = userService.findUser("authuser@test.com").get();
        room.setOwner(user);

        // when
        Long savedId = roomService.create(room);

        // then
        assertEquals(room, roomRepository.findOne(savedId));
    }
}