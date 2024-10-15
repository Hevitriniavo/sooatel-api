package com.fresh.coding.sooatelapi.services.menus.impl;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuWithIngredientsDTO;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.menus.MenuIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MenuIngredientServiceImpl implements MenuIngredientService {
    private final RepositoryFactory factory;

    @Override
    public MenuWithIngredientsDTO findIngredientsByMenuId(Long menuId) {
        var menus = factory.getMenuIngredientRepository().findAllIngredientWithMenuIngredient(menuId);
        var res = new MenuWithIngredientsDTO();
        res.setStatus(menus.getFirst().getMenu().getStatus());
        res.setMenuPrice(menus.getFirst().getMenu().getPrice());
        res.setMenuDesc(menus.getFirst().getMenu().getDescription());
        res.setMenuId(menus.getFirst().getMenu().getId());
        res.setMenuName(menus.getFirst().getMenu().getName());
        var ingredients = res.getIngredients();
        menus.forEach(menuIngredient -> ingredients.add(
                new MenuWithIngredientsDTO.IngredientWithMenuWithUnitName(
                        menuIngredient.getIngredient().getId(),
                        menuIngredient.getIngredient().getUnit() != null ? menuIngredient.getIngredient().getUnit().getId() : null,
                        menuIngredient.getIngredient().getName(),
                        menuIngredient.getQuantity(),
                        menuIngredient.getIngredient().getCreatedAt(),
                        menuIngredient.getIngredient().getUpdatedAt(),
                        menuIngredient.getIngredient().getUnit() != null ? menuIngredient.getIngredient().getUnit().getName() : null
                )
        ));
        return res;
    }


}
