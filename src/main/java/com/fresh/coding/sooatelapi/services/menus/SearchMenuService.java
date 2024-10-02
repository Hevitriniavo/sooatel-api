package com.fresh.coding.sooatelapi.services.menus;


import com.fresh.coding.sooatelapi.dtos.menus.MenusWithCategorySummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuSearch;

import java.util.List;

public interface SearchMenuService {
    Paginate<List<MenusWithCategorySummarized>> findAllMenus(MenuSearch menuSearch, int page, int size);
}
