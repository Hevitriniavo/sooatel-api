package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;

public interface MenuOrderService {
    MenuOrderSummarized createMenuOrder( MenuOrderDTO menuOrderDTO);
}
