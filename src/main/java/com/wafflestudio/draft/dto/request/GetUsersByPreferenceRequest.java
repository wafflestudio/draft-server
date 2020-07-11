package com.wafflestudio.draft.dto.request;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class GetUsersByPreferenceRequest {
    private String regionName;
    private DayOfWeek dayOfWeek;
    @DateTimeFormat(pattern = "HHmmss")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HHmmss")
    private LocalTime endTime;
}
