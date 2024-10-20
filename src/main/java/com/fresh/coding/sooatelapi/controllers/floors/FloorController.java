package com.fresh.coding.sooatelapi.controllers.floors;

import com.fresh.coding.sooatelapi.dtos.floors.FloorDto;
import com.fresh.coding.sooatelapi.dtos.floors.SaveFloorDto;
import com.fresh.coding.sooatelapi.services.floors.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FloorDto createFloor(@RequestBody SaveFloorDto saveFloorDto) {
        return floorService.createFloor(saveFloorDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FloorDto getFloorById(@PathVariable Long id) {
        return floorService.getFloorById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FloorDto> getAllFloors() {
        return floorService.getAllFloors();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FloorDto updateFloor(@PathVariable Long id, @RequestBody SaveFloorDto saveFloorDto) {
        return floorService.updateFloor(id, saveFloorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
    }
}
