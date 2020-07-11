package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Device;
import com.wafflestudio.draft.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Transactional
    public Long create(Device device) {
        deviceRepository.save(device);
        return device.getId();
    }

}
