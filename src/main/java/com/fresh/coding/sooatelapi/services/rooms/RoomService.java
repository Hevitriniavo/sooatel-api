package com.fresh.coding.sooatelapi.services.rooms;

import com.fresh.coding.sooatelapi.dtos.rooms.FloorWithRoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.SaveRoomDTO;

import java.util.List;

public interface RoomService {
    RoomDTO createRoom(SaveRoomDTO saveRoomDTO);
    RoomDTO getRoomById(Long id);
    List<RoomDTO> getAllRooms();
    RoomDTO updateRoom(Long id, SaveRoomDTO saveRoomDTO);
    void deleteRoom(Long id);
    FloorWithRoomDTO findFloorWithRooms(Long floorId);
    List<RoomDTO> getRoomsWithMenuOrders();
}
