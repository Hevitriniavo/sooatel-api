package com.fresh.coding.sooatelapi.dtos.menus;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class SaveMenu extends MenuBase implements Serializable {
    public SaveMenu(Long id, String name, String description, Double price, Long categoryId) {
        super(id, name, description, price, categoryId);
    }
}
