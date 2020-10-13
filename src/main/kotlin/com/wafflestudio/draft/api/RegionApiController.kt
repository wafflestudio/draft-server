package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.response.RegionResponse
import com.wafflestudio.draft.model.Region
import com.wafflestudio.draft.service.RegionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/region")
class RegionApiController(private val regionService: RegionService) {
    @GetMapping("/")
    fun getRegionsV1(@Valid @ModelAttribute request: GetRegionsRequest): List<RegionResponse> {
        var name = request.name
        if (name == null) {
            name = ""
        }
        val regions = regionService.findRegionsByName(name)
        val getRegionsResponse: MutableList<RegionResponse> = ArrayList()
        for (region in regions!!) {
            getRegionsResponse.add(RegionResponse(region))
        }
        return getRegionsResponse
    }
