package com.wafflestudio.draft.api

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

    class GetRegionsRequest {
        var name: String? = null

        override fun equals(o: Any?): Boolean {
            if (o === this) return true
            if (o !is GetRegionsRequest) return false
            val other = o
            if (!other.canEqual(this as Any)) return false
            val `this$name`: Any? = name
            val `other$name`: Any? = other.name
            return if (if (`this$name` == null) `other$name` != null else `this$name` != `other$name`) false else true
        }

        protected fun canEqual(other: Any?): Boolean {
            return other is GetRegionsRequest
        }

        override fun hashCode(): Int {
            val PRIME = 59
            var result = 1
            val `$name`: Any? = name
            result = result * PRIME + (`$name`?.hashCode() ?: 43)
            return result
        }

        override fun toString(): String {
            return "RegionApiController.GetRegionsRequest(name=" + name + ")"
        }
    }

    class RegionResponse {
        var id: Long? = null
        var name: String? = null
        var depth1: String? = null
        var depth2: String? = null
        var depth3: String? = null

        constructor(region: Region?) {
            id = region!!.id
            name = region.name
            depth1 = region.depth1
            depth2 = region.depth2
            depth3 = region.depth3
        }

        constructor() {}

        override fun equals(o: Any?): Boolean {
            if (o === this) return true
            if (o !is RegionResponse) return false
            val other = o
            if (!other.canEqual(this as Any)) return false
            val `this$id`: Any? = id
            val `other$id`: Any? = other.id
            if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
            val `this$name`: Any? = name
            val `other$name`: Any? = other.name
            if (if (`this$name` == null) `other$name` != null else `this$name` != `other$name`) return false
            val `this$depth1`: Any? = depth1
            val `other$depth1`: Any? = other.depth1
            if (if (`this$depth1` == null) `other$depth1` != null else `this$depth1` != `other$depth1`) return false
            val `this$depth2`: Any? = depth2
            val `other$depth2`: Any? = other.depth2
            if (if (`this$depth2` == null) `other$depth2` != null else `this$depth2` != `other$depth2`) return false
            val `this$depth3`: Any? = depth3
            val `other$depth3`: Any? = other.depth3
            return if (if (`this$depth3` == null) `other$depth3` != null else `this$depth3` != `other$depth3`) false else true
        }

        protected fun canEqual(other: Any?): Boolean {
            return other is RegionResponse
        }

        override fun hashCode(): Int {
            val PRIME = 59
            var result = 1
            val `$id`: Any? = id
            result = result * PRIME + (`$id`?.hashCode() ?: 43)
            val `$name`: Any? = name
            result = result * PRIME + (`$name`?.hashCode() ?: 43)
            val `$depth1`: Any? = depth1
            result = result * PRIME + (`$depth1`?.hashCode() ?: 43)
            val `$depth2`: Any? = depth2
            result = result * PRIME + (`$depth2`?.hashCode() ?: 43)
            val `$depth3`: Any? = depth3
            result = result * PRIME + (`$depth3`?.hashCode() ?: 43)
            return result
        }

        override fun toString(): String {
            return "RegionApiController.RegionResponse(id=" + id + ", name=" + name + ", depth1=" + depth1 + ", depth2=" + depth2 + ", depth3=" + depth3 + ")"
        }
    }

}