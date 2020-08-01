package com.wafflestudio.draft.dto.response;

import com.wafflestudio.draft.model.Region;
import lombok.Data;

@Data
public class RegionResponse {
    private Long id;
    private String name;
    private String depth1;
    private String depth2;
    private String depth3;

    public RegionResponse(Region region) {
        this.id = region.getId();
        this.name = region.getName();
        this.depth1 = region.getDepth1();
        this.depth2 = region.getDepth2();
        this.depth3 = region.getDepth3();
    }
}
