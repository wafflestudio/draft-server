package com.wafflestudio.draft.api

import com.wafflestudio.draft.dto.request.GetCourtsRequest
import com.wafflestudio.draft.dto.response.CourtResponse
import com.wafflestudio.draft.dto.response.ListResponse
import com.wafflestudio.draft.service.CourtService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/court")
class CourtApiController(private val courtService: CourtService) {
    @GetMapping("/")
    fun getCourtsV1(@Valid @ModelAttribute request: GetCourtsRequest): ListResponse<CourtResponse> {
        var name = request.name
        if (name == null) {
            name = ""
        }
        val courts = courtService.findCourtsByName(name)
        return ListResponse(courts?.map { CourtResponse(it) } ?: emptyList())
    }
}
