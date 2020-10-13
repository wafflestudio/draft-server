package com.wafflestudio.draft.dto.request

import com.wafflestudio.draft.model.Preference

class SetPreferenceRequest {
    var regionId: Long? = null
    var preferences: List<Preference>? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is SetPreferenceRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$regionId`: Any? = regionId
        val `other$regionId`: Any? = other.regionId
        if (if (`this$regionId` == null) `other$regionId` != null else `this$regionId` != `other$regionId`) return false
        val `this$preferences`: Any? = preferences
        val `other$preferences`: Any? = other.preferences
        return if (if (`this$preferences` == null) `other$preferences` != null else `this$preferences` != `other$preferences`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is SetPreferenceRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$regionId`: Any? = regionId
        result = result * PRIME + (`$regionId`?.hashCode() ?: 43)
        val `$preferences`: Any? = preferences
        result = result * PRIME + (`$preferences`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "SetPreferenceRequest(regionId=" + regionId + ", preferences=" + preferences + ")"
    }
}