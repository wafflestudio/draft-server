package com.wafflestudio.draft.dto

import com.wafflestudio.draft.model.Device

class DeviceDTO {
    data class SetDeviceRequest(
        var deviceToken: String
    )

    data class Response(
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
}