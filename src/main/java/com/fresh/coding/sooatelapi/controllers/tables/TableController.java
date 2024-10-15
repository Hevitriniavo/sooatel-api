package com.fresh.coding.sooatelapi.controllers.tables;

import com.fresh.coding.sooatelapi.dtos.tables.SaveTable;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.dtos.tables.UpdateTableStatus;
import com.fresh.coding.sooatelapi.enums.TableStatus;
import com.fresh.coding.sooatelapi.services.tables.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Validated
@RequestMapping("/tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    public TableSummarized save(@Valid @RequestBody SaveTable toSave){
        return tableService.save(toSave);
    }

    @GetMapping("/all")
    public List<TableSummarized> getAllTables(){
        return tableService.findAllTables();
    }

    @GetMapping("/status")
    public List<TableStatus> getTableStatuses() {
        return Arrays.asList(TableStatus.values());
    }

    @PutMapping("/status")
    public UpdateTableStatus updateStatus(@Valid @RequestBody UpdateTableStatus updateTableStatus) {
        return tableService.updateTableStatus(
                updateTableStatus.getId(),
                updateTableStatus.getStatus()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable Long id) {
        tableService.deleteById(id);
    }
}
