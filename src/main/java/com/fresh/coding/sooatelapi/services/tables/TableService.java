package com.fresh.coding.sooatelapi.services.tables;

import com.fresh.coding.sooatelapi.dtos.tables.SaveTable;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;

import java.util.List;

public interface TableService {
    TableSummarized save(SaveTable toSave);

    List<TableSummarized> findAllTables();

    void deleteById(Long id);

    List<TableSummarized> getTablesWithMenuOrders();
}
