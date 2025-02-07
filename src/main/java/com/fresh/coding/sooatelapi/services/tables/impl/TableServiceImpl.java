package com.fresh.coding.sooatelapi.services.tables.impl;

import com.fresh.coding.sooatelapi.dtos.tables.SaveTable;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.dtos.tables.UpdateTableStatus;
import com.fresh.coding.sooatelapi.entities.RestTable;
import com.fresh.coding.sooatelapi.enums.TableStatus;
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
        RestTable restTable;

        if (toSave.getId() != null) {
            restTable = tableRepository.findById(toSave.getId())
                    .orElseThrow(() -> new HttpNotFoundException("Table not found with ID: " + toSave.getId()));

            BeanUtils.copyProperties(toSave, restTable, "id", "createdAt", "updatedAt");
        } else {
            restTable = new RestTable();
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

    @Override
    @Transactional
    public UpdateTableStatus updateTableStatus(Long id, TableStatus status) {
        var tableRepository = factory.getTableRepository();
        var table = tableRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Table not found with ID: " + id));
        table.setStatus(status);
        var savedTable = tableRepository.save(table);
        return new UpdateTableStatus(savedTable.getId(), savedTable.getStatus());
    }

    @Override
    public void deleteById(Long id) {
        var tableRepository = factory.getTableRepository();
        var menuOrderRepository = factory.getMenuOrderRepository();
        var reservationRepository = factory.getReservationRepository();

        var table = tableRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Table not found with id: " + id));

        reservationRepository.unsetTableReservationById(table.getId());

        if (table.getMenuOrders() != null && !table.getMenuOrders().isEmpty()) {
            for (var menuOrder : table.getMenuOrders()) {
                menuOrder.setTable(null);
                menuOrderRepository.save(menuOrder);
            }
        }

        int deletedTablesCount = tableRepository.deleteTableById(id);
        if (deletedTablesCount > 0) {
            log.info("{} table deleted with id: {}", deletedTablesCount, id);
        }
    }

    @Override
    public List<TableSummarized> getTablesWithMenuOrders() {
        var tableRepository = factory.getTableRepository();
        List<RestTable> tables = tableRepository.findTableWithMenuOrders();
        return tables.stream()
                .map(this::toTableSummarized)
                .collect(Collectors.toList());
    }

    public TableSummarized toTableSummarized(RestTable table) {
        return new TableSummarized(
                table.getId(),
                table.getNumber(),
                table.getCapacity(),
                table.getStatus(),
                table.getCreatedAt(),
                table.getUpdatedAt()
        );
    }
}
