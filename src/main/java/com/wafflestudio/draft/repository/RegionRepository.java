package com.wafflestudio.draft.repository;

import com.wafflestudio.draft.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findById(Long id);

    List<Region> findByNameContaining(String name);
}
