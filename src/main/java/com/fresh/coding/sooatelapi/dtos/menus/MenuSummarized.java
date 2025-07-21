package com.fresh.coding.sooatelapi.dtos.menus;

import com.fresh.coding.sooatelapi.enums.MenuStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public  class MenuSummarized extends MenuBase implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MenuStatus status;

    public MenuSummarized(Long id, String name, String description, Long price, Long categoryId, LocalDateTime createdAt, LocalDateTime updatedAt, MenuStatus status) {
        super(id, name, description, price, categoryId);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

}
