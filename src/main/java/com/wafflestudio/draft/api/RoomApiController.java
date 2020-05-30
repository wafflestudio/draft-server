package com.wafflestudio.draft.api;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.enums.RoomStatus;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.service.RoomService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;

    @PostMapping("/")
    public CreateRoomResponse saveRoomV1(@RequestBody @Valid CreateRoomRequest request, @CurrentUser User currentUser) {
        Room room = new Room();
        room.setOwner(currentUser);
        room.setStartTime(request.getStartTime());
        room.setEndTime(request.getEndTime());
        Long id = roomService.create(room);
        return new CreateRoomResponse(id);
    }

    @Data
    static class CreateRoomRequest {
        @NotNull
        private LocalDateTime startTime;
        @NotNull
        private LocalDateTime endTime;
    }

    @Data
    static class CreateRoomResponse {
        private Long id;

        public CreateRoomResponse(Long id) {
            this.id = id;
        }
    }

    @GetMapping(path = "{id}")
    public GetRoomResponse getRoomV1(@PathVariable("id") Long id) {
        Room room = roomService.findOne(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new GetRoomResponse(room);
    }

    @GetMapping("/")
    public List<GetRoomResponse> getAllRoomsV1() {
        List<Room> rooms = roomService.findRooms();
        List<GetRoomResponse> getAllRoomsResponse = new ArrayList<>();
        for (Room room : rooms) {
            getAllRoomsResponse.add(new GetRoomResponse(room));
        }
        return getAllRoomsResponse;
    }

    @Data
    static class GetRoomResponse {
        private Long id;
        private RoomStatus roomStatus;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private LocalDateTime createdAt;
        private Long ownerId;

        public GetRoomResponse(Room room) {
            this.id = room.getId();
            this.roomStatus = room.getStatus();
            this.startAt = room.getStartTime();
            this.endAt = room.getEndTime();
            this.createdAt = room.getCreatedAt();
            this.ownerId = room.getOwner().getId();
        }
    }
}
