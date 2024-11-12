package com.fresh.coding.sooatelapi.dtos.menu.orders;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateMenuOrderDTO {

    private Long customerId;

    @ToString.Include
    private List<MenuItemDTO> menuItems;

    private Long roomId;

    private Long tableId;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class MenuItemDTO {

        @NotNull(message = "Menu ID is required")
        private Long menuId;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Double quantity;
    }

}
