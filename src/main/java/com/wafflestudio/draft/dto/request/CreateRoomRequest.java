package com.wafflestudio.draft.dto.request;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateRoomRequest {
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private String name;
    @NotNull
    private Long courtId;
}
