package com.fresh.coding.sooatelapi.dtos.rooms;

import com.fresh.coding.sooatelapi.enums.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveRoomDTO implements Serializable  {
    @NotNull(message = "Room number is required")
    private Integer roomNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Status is required")
    private RoomStatus status;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @NotNull(message = "Floor ID is required")
    private Long floorId;
}
