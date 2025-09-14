package com.fresh.coding.sooatelapi.services.tables.impl;

import com.fresh.coding.sooatelapi.dtos.tables.SaveTable;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.TableEntity;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.tables.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TableServiceImpl implements TableService {
    private final RepositoryFactory factory;

    public TableServiceImpl(RepositoryFactory factory) {
        this.factory = factory;
    }

    @Override
    @Transactional
    public TableSummarized save(SaveTable toSave) {
        var tableRepository = factory.getTableRepository();
        TableEntity restTable;

        if (toSave.getId() != null) {
            restTable = tableRepository.findById(toSave.getId())
                    .orElseThrow(() -> new HttpNotFoundException("Table not found with ID: " + toSave.getId()));

            BeanUtils.copyProperties(toSave, restTable, "id", "createdAt", "updatedAt");
        } else {
            restTable = new TableEntity();
            BeanUtils.copyProperties(toSave, restTable);
        }

        var savedTable = tableRepository.save(restTable);

        return toTableSummarized(savedTable);
    }

    @Override
    public List<TableSummarized> findAllTables() {
        var tableRepository = factory.getTableRepository();
        return tableRepository.findAll()
                .stream().map(this::toTableSummarized)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        var tableRepository = factory.getTableRepository();

        var table = tableRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Table not found with id: " + id));


        if (table.getOrders() != null) {
            table.getOrders().clear();
        }

        if (table.getReservations() != null) {
            table.getReservations().clear();
        }

        if (table.getSessionOccupations() != null) {
            table.getSessionOccupations().clear();
        }

        tableRepository.delete(table);

        log.info("Table deleted with id: {}", id);
    }

    @Override
    public List<TableSummarized> getTablesWithMenuOrders() {
        var tableRepository = factory.getTableRepository();
        List<TableEntity> tables = tableRepository.findTableWithMenuOrders();
        return tables.stream()
                .map(this::toTableSummarized)
                .collect(Collectors.toList());
    }

    public TableSummarized toTableSummarized(TableEntity table) {
        return new TableSummarized(
                table.getId(),
                table.getNumber(),
                table.getCapacity(),
                table.getCreatedAt(),
                table.getUpdatedAt()
        );
    }
}
