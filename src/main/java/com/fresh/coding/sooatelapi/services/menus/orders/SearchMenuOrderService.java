package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuOrderSearch;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchMenuOrderService {
    Paginate<List<MenuOrderSummarized>> searchMenuOrders(MenuOrderSearch searchCriteria, Pageable pageable);

    List<MenuOrderSummarized> findAll();
}
