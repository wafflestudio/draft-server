package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Region;
import com.wafflestudio.draft.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public Optional<Region> findRegionById(Long id) {
        return regionRepository.findById(id);
    }

    public List<Region> findRegionsByName(String name) {
        return regionRepository.findByNameContaining(name);
    }
}
