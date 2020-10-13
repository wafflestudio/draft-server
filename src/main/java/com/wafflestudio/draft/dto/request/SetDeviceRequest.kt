package com.wafflestudio.draft.dto.request

import javax.validation.constraints.NotNull

class SetDeviceRequest {
    @NotNull
    var deviceToken: String? = null

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is SetDeviceRequest) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$deviceToken`: Any? = deviceToken
        val `other$deviceToken`: Any? = other.deviceToken
        return if (if (`this$deviceToken` == null) `other$deviceToken` != null else `this$deviceToken` != `other$deviceToken`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is SetDeviceRequest
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$deviceToken`: Any? = deviceToken
        result = result * PRIME + (`$deviceToken`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "SetDeviceRequest(deviceToken=" + deviceToken + ")"
    }
}