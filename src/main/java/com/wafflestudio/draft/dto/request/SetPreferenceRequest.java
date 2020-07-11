package com.wafflestudio.draft.dto.request;

import com.wafflestudio.draft.model.Preference;
import lombok.Data;

import java.util.List;

@Data
public class SetPreferenceRequest {
    private String regionName;
    private List<Preference> preferences;
}
