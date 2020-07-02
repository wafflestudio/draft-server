package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Device;
import com.wafflestudio.draft.model.User;
import com.wafflestudio.draft.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);

    private DeviceRepository deviceRepository;

    public void initializeDevice(Device device) {
        deviceRepository.save(device);
    }
    public void setUser(User user){
    }
}
