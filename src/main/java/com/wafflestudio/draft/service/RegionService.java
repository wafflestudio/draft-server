package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public Region getRegionByName(String regionName) {
        return regionRepository.findByName(regionName);
    }
}
