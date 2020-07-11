package com.wafflestudio.draft.model.response;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.model.enums.RoomStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RoomResponse {
    private Long id;
    private RoomStatus roomStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private LocalDateTime createdAt;
    private Long ownerId;
    private Long courtId;

    public RoomResponse(Room room) {
        this.id = room.getId();
        this.roomStatus = room.getStatus();
        this.startTime = room.getStartTime();
        this.endTime = room.getEndTime();
        this.name = room.getName();
        this.createdAt = room.getCreatedAt();
        this.ownerId = room.getOwner().getId();
        this.courtId = room.getCourt().getId();
    }
}