package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.RegionDTO
import com.wafflestudio.draft.dto.response.ListResponse
import com.wafflestudio.draft.service.RegionService
import com.wafflestudio.draft.service.RoomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/region")
class RegionApiController(
        private val regionService: RegionService,
        private val roomService: RoomService
) {
    @GetMapping("/")
    fun getRegionsV1(@Valid @ModelAttribute request: RegionDTO.Request): ListResponse<RegionDTO.Response> {
        val depth3 = request.depth3.orEmpty()
        val regions = regionService.findRegionsByDepth3(depth3)
        return ListResponse(regions?.map { it.toResponse() }.orEmpty())
    }

    @GetMapping("/room/")
    fun getRoomsV1(): ListResponse<RegionDTO.ResponseWithRooms> {
        val regions = regionService.getRegions().map { it.toResponseWithRooms() }
        return ListResponse(regions)
    }
}
