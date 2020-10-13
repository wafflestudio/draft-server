package com.wafflestudio.draft.repository

import com.wafflestudio.draft.model.Device
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRepository : JpaRepository<Device?, Long?>
