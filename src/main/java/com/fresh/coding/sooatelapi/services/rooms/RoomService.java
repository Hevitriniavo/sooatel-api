package com.fresh.coding.sooatelapi.services.rooms;

import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.SaveRoomDTO;
import com.fresh.coding.sooatelapi.enums.RoomStatus;

import java.util.List;

public interface RoomService {
    RoomDTO createRoom(SaveRoomDTO saveRoomDTO);
    RoomDTO getRoomById(Long id);
    List<RoomDTO> getAllRooms();
    RoomDTO updateRoom(Long id, SaveRoomDTO saveRoomDTO);
    void deleteRoom(Long id);

    RoomDTO updateRoomStatus(Long id, RoomStatus status);
}
