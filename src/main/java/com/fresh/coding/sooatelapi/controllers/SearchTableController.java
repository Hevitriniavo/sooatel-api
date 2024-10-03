package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.TableSearch;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.services.tables.SearchTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/tables")
@RequiredArgsConstructor
public class SearchTableController {

    private final SearchTableService searchTableService;

    @GetMapping
    public Paginate<List<TableSummarized>> getAllTables(
            @ModelAttribute TableSearch tableSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return searchTableService.findAllTables(tableSearch, page, size);
    }

}
