package com.fresh.coding.sooatelapi.services.floors;

import com.fresh.coding.sooatelapi.dtos.floors.FloorDto;
import com.fresh.coding.sooatelapi.dtos.floors.SaveFloorDto;

import java.util.List;

public interface FloorService {
    FloorDto createFloor(SaveFloorDto saveFloorDto);

    FloorDto getFloorById(Long id);

    List<FloorDto> getAllFloors();

    FloorDto updateFloor(Long id, SaveFloorDto saveFloorDto);

    void deleteFloor(Long id);
}
