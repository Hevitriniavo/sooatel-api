package com.fresh.coding.sooatelapi.services.tables;

import com.fresh.coding.sooatelapi.dtos.tables.SaveTable;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.dtos.tables.UpdateTableStatus;
import com.fresh.coding.sooatelapi.enums.TableStatus;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface TableService {
    TableSummarized save(SaveTable toSave);

    List<TableSummarized> findAllTables();

    UpdateTableStatus updateTableStatus(@NotNull Long id, @NotNull TableStatus status);

    void deleteById(Long id);
}
