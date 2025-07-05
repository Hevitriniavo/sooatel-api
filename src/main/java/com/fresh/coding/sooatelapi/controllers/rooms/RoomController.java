package com.fresh.coding.sooatelapi.controllers.rooms;

import com.fresh.coding.sooatelapi.dtos.rooms.FloorWithRoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.SaveRoomDTO;
import com.fresh.coding.sooatelapi.services.rooms.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDTO createRoom(@Valid @RequestBody SaveRoomDTO saveRoomDTO) {
        return roomService.createRoom(saveRoomDTO);
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }



    @GetMapping("/floor/{floorId}")
    @ResponseStatus(HttpStatus.OK)
    public FloorWithRoomDTO getFloorWithRooms(@PathVariable Long floorId) {
        return roomService.findFloorWithRooms(floorId);
    }


    @GetMapping
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/rooms-with-menu-orders")
    public List<RoomDTO> getRoomsWithMenuOrders() {
        return roomService.getRoomsWithMenuOrders();
    }

    @PutMapping("/{id}")
    public RoomDTO updateRoom(@PathVariable Long id, @Valid @RequestBody SaveRoomDTO saveRoomDTO) {
        return roomService.updateRoom(id, saveRoomDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
