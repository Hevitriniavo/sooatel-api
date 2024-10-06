package com.fresh.coding.sooatelapi.dtos.menus;

import com.fresh.coding.sooatelapi.enums.MenuStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class SaveMenu extends MenuBase implements Serializable {
    private MenuStatus status;
    public SaveMenu(Long id, String name, String description, Double price, Long categoryId, MenuStatus status) {
        super(id, name, description, price, categoryId);
        this.status = status;
    }
}
