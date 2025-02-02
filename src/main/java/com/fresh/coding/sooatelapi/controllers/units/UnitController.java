package com.fresh.coding.sooatelapi.controllers.units;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.UnitSearch;
import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.dtos.unit.UpdateUnit;
import com.fresh.coding.sooatelapi.services.units.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnitSummarized createUnit(@RequestBody @Valid CreateUnit toCreate) {
        return unitService.create(toCreate);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UnitSummarized updateUnit(@RequestBody @Valid UpdateUnit toUpdate) {
        return unitService.update(toUpdate);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnit(@PathVariable Long id) {
         unitService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<UnitSummarized>> getAllUnits(
            @ModelAttribute UnitSearch unitSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return unitService.getAllUnits(unitSearch, page, size);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UnitSummarized> getAllUnits() {
        return unitService.getAllUnits();
    }
}
