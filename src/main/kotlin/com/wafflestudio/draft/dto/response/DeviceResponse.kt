package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Device

data class DeviceResponse(
        var id: Long? = null,
        var deviceToken: String? = null,
        var userId: Long? = null,
        var username: String? = null
) {
    constructor(device: Device) {
        id = device.id
        deviceToken = device.deviceToken
        userId = device.user!!.id
        username = device.user!!.username
    }
}
