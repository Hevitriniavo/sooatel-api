package com.fresh.coding.sooatelapi.services.rooms;

import com.fresh.coding.sooatelapi.dtos.rooms.FloorWithRoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.SaveRoomDTO;
import com.fresh.coding.sooatelapi.entities.Room;
import com.fresh.coding.sooatelapi.enums.RoomStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RepositoryFactory repositoryFactory;

    @Override
    public RoomDTO createRoom(SaveRoomDTO saveRoomDTO) {
        var roomRepository = repositoryFactory.getRoomRepository();
        var floorRepository = repositoryFactory.getFloorRepository();

        var floor = floorRepository.findById(saveRoomDTO.getFloorId())
                .orElseThrow(() -> new HttpNotFoundException("Floor not found"));

        var room = new Room();
        BeanUtils.copyProperties(saveRoomDTO, room, "floorId");
        room.setFloor(floor);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);
        return mapToDTO(room);
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        var roomRepository = repositoryFactory.getRoomRepository();
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Room not found"));
        return mapToDTO(room);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        var roomRepository = repositoryFactory.getRoomRepository();
        return roomRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO updateRoom(Long id, SaveRoomDTO saveRoomDTO) {
        var roomRepository = repositoryFactory.getRoomRepository();
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Room not found"));

        var floor = repositoryFactory.getFloorRepository().findById(saveRoomDTO.getFloorId())
                .orElseThrow(() -> new HttpNotFoundException("Floor not found"));

        BeanUtils.copyProperties(saveRoomDTO, room, "floorId");
        room.setFloor(floor);
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);
        return mapToDTO(room);
    }

    @Override
    public void deleteRoom(Long id) {
        var roomRepository = repositoryFactory.getRoomRepository();
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Room not found"));
        roomRepository.delete(room);
    }

    @Override
    public RoomDTO updateRoomStatus(Long id, RoomStatus status) {
        var roomRepository = repositoryFactory.getRoomRepository();
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Room not found"));
        room.setStatus(status);
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);
        return mapToDTO(room);
    }

    @Override
    public FloorWithRoomDTO findFloorWithRooms(Long floorId) {
        var floorRepository = repositoryFactory.getFloorRepository();
        var floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new HttpNotFoundException("Floor not found"));

        var roomRepository = repositoryFactory.getRoomRepository();
        var rooms = roomRepository.findByFloorId(floorId);

        List<RoomDTO> roomDTOs = rooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new FloorWithRoomDTO(
                floor.getId(),
                floor.getFloorNumber(),
                floor.getDescription(),
                floor.getCreatedAt(),
                floor.getUpdatedAt(),
                roomDTOs
        );
    }

    public List<RoomDTO> getRoomsWithMenuOrders() {
        var roomRepository = repositoryFactory.getRoomRepository();
        List<Room> rooms = roomRepository.findRoomsWithMenuOrders();
        return rooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    private RoomDTO mapToDTO(Room room) {
        var roomDTO = new RoomDTO();
        BeanUtils.copyProperties(room, roomDTO);
        if (room.getFloor() != null){
            roomDTO.setFloorId(room.getFloor().getId());
        }
        return roomDTO;
    }
}
