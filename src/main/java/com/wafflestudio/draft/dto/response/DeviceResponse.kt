package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Device

class DeviceResponse {
    var id: Long? = null
    var deviceToken: String? = null
    var userId: Long? = null
    var username: String? = null

    constructor(device: Device) {
        id = device.id
        deviceToken = device.deviceToken
        userId = device.user!!.id
        username = device.user!!.username
    }

    constructor() {}

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is DeviceResponse) return false
        val other = o
        if (!other.canEqual(this as Any)) return false
        val `this$id`: Any? = id
        val `other$id`: Any? = other.id
        if (if (`this$id` == null) `other$id` != null else `this$id` != `other$id`) return false
        val `this$deviceToken`: Any? = deviceToken
        val `other$deviceToken`: Any? = other.deviceToken
        if (if (`this$deviceToken` == null) `other$deviceToken` != null else `this$deviceToken` != `other$deviceToken`) return false
        val `this$userId`: Any? = userId
        val `other$userId`: Any? = other.userId
        if (if (`this$userId` == null) `other$userId` != null else `this$userId` != `other$userId`) return false
        val `this$username`: Any? = username
        val `other$username`: Any? = other.username
        return if (if (`this$username` == null) `other$username` != null else `this$username` != `other$username`) false else true
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is DeviceResponse
    }

    override fun hashCode(): Int {
        val PRIME = 59
        var result = 1
        val `$id`: Any? = id
        result = result * PRIME + (`$id`?.hashCode() ?: 43)
        val `$deviceToken`: Any? = deviceToken
        result = result * PRIME + (`$deviceToken`?.hashCode() ?: 43)
        val `$userId`: Any? = userId
        result = result * PRIME + (`$userId`?.hashCode() ?: 43)
        val `$username`: Any? = username
        result = result * PRIME + (`$username`?.hashCode() ?: 43)
        return result
    }

    override fun toString(): String {
        return "DeviceResponse(id=" + id + ", deviceToken=" + deviceToken + ", userId=" + userId + ", username=" + username + ")"
    }
}