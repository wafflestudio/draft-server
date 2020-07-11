package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Court;
import com.wafflestudio.draft.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CourtService {
    @Autowired
    private CourtRepository courtRepository;

    public Optional<Court> getCourtById(Long id) {
        return courtRepository.findById(id);
    }
}
