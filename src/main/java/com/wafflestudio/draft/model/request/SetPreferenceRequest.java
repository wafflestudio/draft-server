package com.wafflestudio.draft.model.request;

import com.wafflestudio.draft.model.Preference;
import lombok.Data;

import java.util.List;

@Data
public class SetPreferenceRequest {
    private String regionName;
    private List<Preference> preferences;
}
