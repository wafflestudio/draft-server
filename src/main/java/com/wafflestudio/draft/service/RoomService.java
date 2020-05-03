package com.wafflestudio.draft.service;

import com.wafflestudio.draft.model.Room;
import com.wafflestudio.draft.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Long create(Room room) {
        roomRepository.save(room);
        return room.getId();
    }

    public List<Room> findRooms() {
        return roomRepository.findAll();
    }

    public Room findOne(Long roomId) {
        return roomRepository.findOne(roomId);
    }
}
