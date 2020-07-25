package com.wafflestudio.draft.api;

import com.wafflestudio.draft.dto.request.CreateRoomRequest;
import com.wafflestudio.draft.dto.request.GetRoomsRequest;
import com.wafflestudio.draft.dto.request.PutRoomRequest;
import com.wafflestudio.draft.dto.response.ParticipantsResponse;
import com.wafflestudio.draft.dto.response.RoomResponse;
import com.wafflestudio.draft.model.Court;
import com.wafflestudio.draft.model.Participant;
import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.model.enums.RoomStatus;
import com.wafflestudio.draft.security.CurrentUser;
import com.wafflestudio.draft.service.CourtService;
import com.wafflestudio.draft.service.FCMService;
import com.wafflestudio.draft.service.ParticipantService;
import com.wafflestudio.draft.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomApiController {

    private final FCMService fcmService;
    // FIXME: Use fcmService.send(message) when room create

    private final ParticipantService participantService;
    private final RoomService roomService;
    private final CourtService courtService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse saveRoomV1(@RequestBody @Valid CreateRoomRequest request, @CurrentUser User currentUser) {
        Room room = new Room();
        room.setOwner(currentUser);
        room.setStartTime(request.getStartTime());
        room.setEndTime(request.getEndTime());
        room.setName(request.getName());
        Optional<Court> court = courtService.getCourtById(request.getCourtId());
        if (court.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        room.setCourt(court.get());
        roomService.save(room);
        participantService.addParticipants(room, currentUser);
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
    public List<RoomResponse> getRoomsV1(@Valid @ModelAttribute GetRoomsRequest request) {
        String name = request.getName();
        if (name == null) {
            name = "";
        }
        Long courtId = request.getCourtId();
        // FIXME: startTime and endTime for getting rooms will be used later
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        List<Room> rooms = roomService.findRooms(name, courtId, startTime, endTime);
        List<RoomResponse> getRoomsResponse = new ArrayList<>();
        for (Room room : rooms) {
            getRoomsResponse.add(new RoomResponse(room));
        }
        return getRoomsResponse;
    }

    @PostMapping(path = "{id}/participant/")
    public ParticipantsResponse participate(@PathVariable("id") Long id, @CurrentUser User currentUser) {
        Room room = roomService.findOne(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Participant> participants = room.getParticipants();
        for (Participant participant : participants) {
            if (currentUser.getId() == participant.getUser().getId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        if (participants.size() >= room.getCourt().getCapacity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return participantService.addParticipants(room, currentUser);
    }

    @GetMapping(path = "{id}/participant/")
    public ParticipantsResponse getParticipants(@PathVariable("id") Long id) {
        Room room = roomService.findOne(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return participantService.getParticipants(room);
    }

    @PutMapping(path = "{id}")
    public RoomResponse putRoomV1(@PathVariable("id") Long id, @RequestBody @Valid PutRoomRequest request) {
        Room room = roomService.findOne(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // FIXME: we should find a smarter way...
        LocalDateTime startTime = request.getStartTime();
        if (startTime != null) {
            room.setStartTime(startTime);
        }
        LocalDateTime endTime = request.getEndTime();
        if (endTime != null) {
            room.setEndTime(endTime);
        }
        String name = request.getName();
        if (name != null) {
            room.setName(name);
        }
        RoomStatus status = request.getStatus();
        if (status != null) {
            room.setStatus(status);
        }

        roomService.save(room);
        return new RoomResponse(room);
    }
}
