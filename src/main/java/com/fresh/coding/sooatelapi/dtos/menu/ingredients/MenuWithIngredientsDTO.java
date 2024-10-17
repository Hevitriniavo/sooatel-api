package com.fresh.coding.sooatelapi.dtos.menu.ingredients;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fresh.coding.sooatelapi.enums.MenuStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuWithIngredientsDTO implements Serializable {
    private Long menuId;
    private String menuName;
    private String menuDesc;
    private Double menuPrice;
    private MenuStatus status;
    private List<IngredientWithMenuWithUnitName> ingredients = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class IngredientWithMenuWithUnitName {
        private Long id;
        private Long unitId;
        private String ingredientName;
        private Double quantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String unitName;
    }
}
