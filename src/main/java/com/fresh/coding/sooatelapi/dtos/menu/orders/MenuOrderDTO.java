package com.fresh.coding.sooatelapi.dtos.menu.orders;


import com.fresh.coding.sooatelapi.enums.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuOrderDTO implements Serializable {

    private Long customerId;

    private Long roomId;

    private Integer tableNumber;
    private Integer roomNumber;

    private Long tableId;

    private LocalDateTime orderDate;

    private List<MenuItemSummarizedDTO> menuItems;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuItemSummarizedDTO {

        private Long menuId;

        private Double quantity;

        private Double totalPrice;

        private OrderStatus status;
    }

}
