package com.fresh.coding.sooatelapi.dtos.tables;

import com.fresh.coding.sooatelapi.enums.TableStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public final class TableSummarized extends TableBase implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public TableSummarized(Long id, Integer number, Integer capacity, TableStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, number, capacity, status);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
