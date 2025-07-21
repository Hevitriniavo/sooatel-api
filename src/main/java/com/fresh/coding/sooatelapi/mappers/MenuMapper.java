package com.fresh.coding.sooatelapi.mappers;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.entities.Menu;

public class MenuMapper {
    public static MenuSummarized toSummarized(Menu menu) {
        if (menu == null) return null;

        return new MenuSummarized(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getMenuGroup() != null ? menu.getMenuGroup().getId() : null,
                menu.getCreatedAt(),
                menu.getUpdatedAt(),
                menu.getStatus()
        );
    }
}
