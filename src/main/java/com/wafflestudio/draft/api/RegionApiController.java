package com.wafflestudio.draft.api;

import com.wafflestudio.draft.dto.request.GetRegionsRequest;
import com.wafflestudio.draft.dto.response.RegionResponse;
import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
public class RegionApiController {

    private final RegionService regionService;

    @GetMapping("/")
    public List<RegionResponse> getRegionsV1(@Valid @ModelAttribute GetRegionsRequest request) {
        String name = request.getName();
        if (name == null) {
            name = "";
        }
        List<Region> regions = regionService.findRegionsByName(name);
        List<RegionResponse> getRegionsResponse = new ArrayList<>();
        for (Region region : regions) {
            getRegionsResponse.add(new RegionResponse(region));
        }
        return getRegionsResponse;
    }
}
