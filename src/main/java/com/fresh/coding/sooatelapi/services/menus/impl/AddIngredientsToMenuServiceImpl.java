package com.fresh.coding.sooatelapi.services.menus.impl;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuIngredientDto;
import com.fresh.coding.sooatelapi.entities.MenuIngredient;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.menus.AddIngredientsToMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
public class AddIngredientsToMenuServiceImpl implements AddIngredientsToMenuService {

    private final RepositoryFactory repositoryFactory;

    @Override
    @Transactional
    public void addIngredientsToMenu(MenuIngredientDto menuIngredientDto) {
        var menu = repositoryFactory.getMenuRepository().findById(menuIngredientDto.getMenuId())
                .orElseThrow(() -> new HttpNotFoundException("Menu non trouvé avec l'ID : " + menuIngredientDto.getMenuId()));

        for (var ingredientQuantityDto : menuIngredientDto.getIngredients()) {
            var ingredient = repositoryFactory.getIngredientRepository().findById(ingredientQuantityDto.getIngredientId())
                    .orElseThrow(() -> new HttpNotFoundException("Ingrédient non trouvé avec l'ID : " + ingredientQuantityDto.getIngredientId()));

            boolean exists = repositoryFactory.getMenuIngredientRepository()
                    .existsByMenuIdAndIngredientId(menuIngredientDto.getMenuId(), ingredientQuantityDto.getIngredientId());

            if (exists) {
                throw new HttpBadRequestException("L'ingrédient est déjà présent dans le menu.");
            }

            var menuIngredient = MenuIngredient.builder()
                    .menu(menu)
                    .ingredient(ingredient)
                    .quantity(ingredientQuantityDto.getQuantity())
                    .build();

            repositoryFactory.getMenuIngredientRepository().save(menuIngredient);
        }
    }


}
