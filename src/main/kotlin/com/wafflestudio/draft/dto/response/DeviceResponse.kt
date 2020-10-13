package com.wafflestudio.draft.dto.response

import com.wafflestudio.draft.model.Device

data class DeviceResponse(
        var id: Long? = null,
        var deviceToken: String? = null,
        var userId: Long? = null,
        var username: String? = null
) {

    constructor(device: Device) : this(
            device.id,
            device.deviceToken,
            device.user!!.id,
            device.user!!.username
    )
}
