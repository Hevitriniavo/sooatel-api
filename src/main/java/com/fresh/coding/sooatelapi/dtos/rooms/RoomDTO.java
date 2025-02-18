package com.fresh.coding.sooatelapi.dtos.rooms;

import com.fresh.coding.sooatelapi.enums.RoomStatus;
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
    private Integer roomNumber;
    private Integer capacity;
    private RoomStatus status;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
