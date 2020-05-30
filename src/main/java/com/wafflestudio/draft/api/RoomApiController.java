package com.wafflestudio.draft.api;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.service.FCMService;
import com.wafflestudio.draft.service.RoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final FCMService fcmService;
    // FIXME: Use fcmService.send(message) when room create

    private final RoomService roomService;

    @PostMapping("/api/v1/room/")
    public CreateRoomResponse saveRoomV1() {
        // FIXME: These codes are temporary until basic User APIs are implemented.
        Room room = new Room();
        User user = new User("ROOMTESTER", "roomtestuser@test.com");
        room.setOwner(user);

        Long id = roomService.create(room);
        return new CreateRoomResponse(id);
    }

    @Data
    static class CreateRoomResponse {
        private Long id;

        public CreateRoomResponse(Long id) {
            this.id = id;
        }
    }
}
