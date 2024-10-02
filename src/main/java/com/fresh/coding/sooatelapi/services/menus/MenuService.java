package com.fresh.coding.sooatelapi.services.menus;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.SaveMenu;

import java.util.List;

public interface MenuService {
    MenuSummarized save(SaveMenu toSave);

    List<MenuSummarized> findALlMenus();
}
