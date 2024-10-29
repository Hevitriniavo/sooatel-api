package com.fresh.coding.sooatelapi.services.menus.impl;

import com.fresh.coding.sooatelapi.dtos.menu.ingredients.MenuWithIngredientsDTO;
import com.fresh.coding.sooatelapi.entities.MenuIngredient;
import com.fresh.coding.sooatelapi.enums.MenuStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.menus.MenuIngredientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MenuIngredientServiceImpl implements MenuIngredientService {

    private final RepositoryFactory repositoryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MenuWithIngredientsDTO findIngredientsByMenuId(Long menuId) {
        var jpql = """
            SELECT DISTINCT m.id, m.name, m.description, m.price, m.status,
                   i.id, i.name, mi.quantity, u.name, mi.createdAt, mi.updatedAt
            FROM Menu m
            LEFT JOIN m.menuIngredients mi
            LEFT JOIN mi.ingredient i
            LEFT JOIN i.unit u
            WHERE m.id = :menuId
        """;

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("menuId", menuId);
        List<Object[]> results = query.getResultList();


        if (results.isEmpty()) {
            return null;
        }

        MenuWithIngredientsDTO dto = new MenuWithIngredientsDTO();
        Object[] firstRow = results.getFirst();

        dto.setMenuId((Long) firstRow[0]);
        dto.setMenuName((String) firstRow[1]);
        dto.setMenuDesc((String) firstRow[2]);
        dto.setMenuPrice((Double) firstRow[3]);
        dto.setStatus((MenuStatus) firstRow[4]);

        var ingredients = results.stream()
                .map(row -> {
                    MenuWithIngredientsDTO.IngredientWithMenuWithUnitName ingredient = new MenuWithIngredientsDTO.IngredientWithMenuWithUnitName();
                    ingredient.setId((Long) row[5]);
                    ingredient.setIngredientName((String) row[6]);
                    ingredient.setQuantity((Double) row[7]);
                    ingredient.setUnitName((String) row[8]);
                    ingredient.setCreatedAt((LocalDateTime) row[9]);
                    ingredient.setUpdatedAt((LocalDateTime) row[10]);
                    return ingredient;
                })
                .collect(Collectors.toList());

        dto.setIngredients(ingredients);

        return dto;
    }

    @Override
    @Transactional
    public void deleteMenuIngredientById(Long menuIngredientId) {
        var menuIngredientRepository = repositoryFactory.getMenuIngredientRepository();

        if (!menuIngredientRepository.existsById(menuIngredientId)) {
            throw new HttpNotFoundException("MenuIngredient not found with ID: " + menuIngredientId);
        }

        menuIngredientRepository.deleteById(menuIngredientId);
    }

    @Override
    @Transactional
    public void deleteMenuIngredientByMenuIdAndIngredientId(Long menuId, Long ingredientId) {
        var menuIngredientRepository = repositoryFactory.getMenuIngredientRepository();
        MenuIngredient menuIngredient = menuIngredientRepository.findByMenuIdAndIngredientId(menuId, ingredientId)
                .orElseThrow(() -> new HttpNotFoundException("Menu ingredient not found"));
        menuIngredientRepository.delete(menuIngredient);
    }
}
