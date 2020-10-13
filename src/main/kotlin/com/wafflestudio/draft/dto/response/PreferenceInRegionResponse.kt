package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Preference
import com.wafflestudio.draft.model.Region
import java.util.*

class PreferenceInRegionResponse {
    var id: Long? = null
    var name: String? = null
    var depth1: String? = null
    var depth2: String? = null
    var depth3: String? = null
    var preferences: List<PreferenceResponse>? = null

    constructor(region: Region, preferences: List<Preference?>?) {
        id = region.id
        name = region.name
        depth1 = region.depth1
        depth2 = region.depth2
        depth3 = region.depth3
        val preferenceResponses: MutableList<PreferenceResponse> = ArrayList()
        for (preference in preferences!!) {
            preferenceResponses.add(PreferenceResponse(preference))
        }
        this.preferences = preferenceResponses
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is PreferenceInRegionResponse) return false
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
        if (if (`this$depth3` == null) `other$depth3` != null else `this$depth3` != `other$depth3`) return false
        val `this$preferences`: Any? = preferences
        val `other$preferences`: Any? = other.preferences
        return if (if (`this$preferences` == null) `other$preferences` != null else `this$preferences` != `other$preferences`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is PreferenceInRegionResponse
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
        val `$preferences`: Any? = preferences
        result = result * PRIME + (`$preferences`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "PreferenceInRegionResponse(id=" + id + ", name=" + name + ", depth1=" + depth1 + ", depth2=" + depth2 + ", depth3=" + depth3 + ", preferences=" + preferences + ")"
    }
}