package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Preference;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class PreferenceResponse {
    private Long id;
    private LocalTime startAt;
    private LocalTime endAt;
    private Long regionId;
    private DayOfWeek dayOfWeek;

    public PreferenceResponse(Preference preference) {
        this.id = preference.getId();
        this.startAt = preference.getStartAt();
        this.endAt = preference.getEndAt();
        this.regionId = preference.getRegion().getId();
        this.dayOfWeek = preference.getDayOfWeek();
    }
}
