package com.wafflestudio.draft.dto.request;

import com.wafflestudio.draft.model.Preference;
import lombok.Data;

import java.util.List;

@Data
public class SetPreferenceRequest {
    private Long regionId;
    private List<Preference> preferences;
}
