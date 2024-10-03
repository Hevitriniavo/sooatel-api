package com.fresh.coding.sooatelapi.dtos.tables;

import com.fresh.coding.sooatelapi.enums.TableStatus;

import java.io.Serializable;

public final class SaveTable extends TableBase implements Serializable {
    public SaveTable(Long id, Integer number, Integer capacity, TableStatus status) {
        super(id, number, capacity, status);
    }
}
