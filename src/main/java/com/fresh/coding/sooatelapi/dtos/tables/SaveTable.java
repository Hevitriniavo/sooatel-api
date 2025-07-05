package com.fresh.coding.sooatelapi.dtos.tables;

import java.io.Serializable;

public final class SaveTable extends TableBase implements Serializable {
    public SaveTable(Long id, Long number, Long capacity) {
        super(id, number, capacity);
    }
}
