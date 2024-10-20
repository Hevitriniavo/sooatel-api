package com.fresh.coding.sooatelapi.dtos.rooms;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FloorWithRoomDTO {
    private Long id;
    private Integer floorNumber;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<RoomDTO> rooms;
}
