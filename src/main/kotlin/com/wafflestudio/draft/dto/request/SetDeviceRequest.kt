package com.wafflestudio.draft.dto.request

import javax.validation.constraints.NotNull

data class SetDeviceRequest (
    var deviceToken: String
)