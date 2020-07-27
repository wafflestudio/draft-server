package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomsOfUserResponse {
    private Long id;
    private String username;
    private String email;
    private List<RoomResponse> rooms;

    public RoomsOfUserResponse(User user, List<Room> rooms) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (Room room : rooms) {
            roomResponses.add(new RoomResponse(room));
        }
        this.rooms = roomResponses;
    }
}
