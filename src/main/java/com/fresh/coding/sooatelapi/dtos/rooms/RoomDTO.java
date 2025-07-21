package com.fresh.coding.sooatelapi.dtos.rooms;

import com.fresh.coding.sooatelapi.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO implements Serializable {
    private Long id;
    private Long floorId;
    private Long number;
    private Long capacity;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.floorId = room.getFloor().getId();
        this.number = room.getNumber();
        this.capacity = room.getCapacity();
        this.price = room.getPrice();
        this.createdAt = room.getCreatedAt();
        this.updatedAt = room.getUpdatedAt();
    }
}
