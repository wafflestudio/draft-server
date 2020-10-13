package com.wafflestudio.draft.service

import com.wafflestudio.draft.model.Device
import com.wafflestudio.draft.repository.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceService {
    @Autowired
    private val deviceRepository: DeviceRepository? = null

    @Transactional
    fun create(device: Device): Long? {
        deviceRepository!!.save(device)
        return device.id
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FCMService::class.java)
    }
}