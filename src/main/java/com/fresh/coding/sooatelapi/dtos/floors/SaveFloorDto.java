package com.fresh.coding.sooatelapi.dtos.floors;

import jakarta.validation.constraints.NotBlank;
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
public class SaveFloorDto implements Serializable {

    @NotNull(message = "Floor number is required")
    private Integer floorNumber;

    @NotBlank(message = "Description is required")
    private String description;
}
