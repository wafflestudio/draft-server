package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Preference;
import com.wafflestudio.draft.model.Region;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PreferenceInRegionResponse {
    private Long id;
    private String name;
    private String depth1;
    private String depth2;
    private String depth3;
    private List<PreferenceResponse> preferences;

    public PreferenceInRegionResponse(Region region, List<Preference> preferences) {
        this.id = region.getId();
        this.name = region.getName();
        this.depth1 = region.getDepth1();
        this.depth2 = region.getDepth2();
        this.depth3 = region.getDepth3();
        List<PreferenceResponse> preferenceResponses = new ArrayList<>();
        for (Preference preference : preferences) {
            preferenceResponses.add(new PreferenceResponse(preference));
        }
        this.preferences = preferenceResponses;
    }
}
