package com.fresh.coding.sooatelapi.dtos.tables;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public final class TableSummarized extends TableBase implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public TableSummarized(Long id, Long number, Long capacity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, number, capacity);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
