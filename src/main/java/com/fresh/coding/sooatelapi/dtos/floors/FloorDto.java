package com.fresh.coding.sooatelapi.dtos.floors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FloorDto  implements Serializable {
    private Long id;
    private Integer floorNumber;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
