package com.fresh.coding.sooatelapi.services.menus;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuIngredientDto;


@FunctionalInterface
public interface AddIngredientsToMenuService {
    void addIngredientsToMenu(MenuIngredientDto menuIngredientDto);
}
