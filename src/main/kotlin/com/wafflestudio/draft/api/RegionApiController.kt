package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.dto.response.ListResponse
import com.wafflestudio.draft.dto.response.PageResponse
import com.wafflestudio.draft.service.RegionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.data.domain.PageRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/region")
class RegionApiController(
        private val regionService: RegionService
) {
    @GetMapping("/")
    fun getRegionsV1(@Valid @ModelAttribute request: RegionDTO.Request): PageResponse<RegionDTO.Summary> {
        val depth3 = request.depth3.orEmpty()
        val page = request.page
        val pageable = PageRequest.of(page, 20)

        val regions = regionService.findRegionsByDepth3(depth3, pageable)
        return PageResponse(regions)
    }

    @GetMapping("/room/")
    fun getRoomsV1(): ListResponse<RegionDTO.ResponseWithRooms> {
        val regions = regionService.getRegions().map { it.toResponseWithRooms() }
        return ListResponse(regions)
    }
}
