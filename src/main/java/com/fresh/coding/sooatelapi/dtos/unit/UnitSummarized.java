package com.fresh.coding.sooatelapi.dtos.unit;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public final class UnitSummarized extends UnitBase implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UnitSummarized(Long id, String name, String abbreviation, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(name, abbreviation);
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
