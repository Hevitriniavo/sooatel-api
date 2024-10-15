package com.fresh.coding.sooatelapi.services.menus;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuWithIngredientsDTO;


public interface MenuIngredientService {
    MenuWithIngredientsDTO findIngredientsByMenuId(Long menuId);
}
