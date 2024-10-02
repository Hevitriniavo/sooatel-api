package com.fresh.coding.sooatelapi.dtos.menus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public final class MenuSummarized extends MenuBase implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MenuSummarized(Long id, String name, String description, Double price, Long categoryId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, name, description, price, categoryId);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
