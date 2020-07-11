package com.wafflestudio.draft.api;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.request.CreateRoomRequest;
import com.wafflestudio.draft.model.request.GetRoomsRequest;
import com.wafflestudio.draft.model.response.RoomResponse;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.service.FCMService;
import com.wafflestudio.draft.model.enums.RoomStatus;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomApiController {

    private final FCMService fcmService;
    // FIXME: Use fcmService.send(message) when room create

    private final RoomService roomService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse saveRoomV1(@RequestBody @Valid CreateRoomRequest request, @CurrentUser User currentUser) {
        Room room = new Room();
        room.setOwner(currentUser);
        room.setStartTime(request.getStartTime());
        room.setEndTime(request.getEndTime());
        room.setName(request.getName());
        Long id = roomService.create(room);
        return new RoomResponse(room);
    }

    @GetMapping(path = "{id}")
    public RoomResponse getRoomV1(@PathVariable("id") Long id) {
        Room room = roomService.findOne(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new RoomResponse(room);
    }

    @GetMapping("/")
    public List<RoomResponse> getAllRoomsV1(@Valid @ModelAttribute GetRoomsRequest request) {
        String name = request.getName();
        if (name == null) {
            name = "";
        }
        // FIXME: startTime and endTime for getting rooms will be used later
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        List<Room> rooms = roomService.findRooms(name, startTime, endTime);
        List<RoomResponse> getAllRoomsResponse = new ArrayList<>();
        for (Room room : rooms) {
            getAllRoomsResponse.add(new RoomResponse(room));
        }
        return getAllRoomsResponse;
    }

}
