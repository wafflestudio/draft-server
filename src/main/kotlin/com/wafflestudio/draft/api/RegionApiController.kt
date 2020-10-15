package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.GetRegionsRequest
import com.wafflestudio.draft.dto.response.RegionResponse
import com.wafflestudio.draft.service.RegionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
        return regions?.map {
            RegionResponse(it)
        } ?: emptyList()
    }
}
