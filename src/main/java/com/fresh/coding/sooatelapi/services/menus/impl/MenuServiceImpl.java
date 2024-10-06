package com.fresh.coding.sooatelapi.services.menus.impl;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.SaveMenu;
import com.fresh.coding.sooatelapi.entities.Menu;
import com.fresh.coding.sooatelapi.enums.MenuStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.menus.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final RepositoryFactory factory;

    @Override
    public MenuSummarized save(SaveMenu toSave) {
        var menuRepository = factory.getMenuRepository();
        var categoryRepository = factory.getCategoryRepository();

        var category = categoryRepository.findById(toSave.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + toSave.getCategoryId()));

        var menu = toSave.getId() != null ?
                menuRepository.findById(toSave.getId())
                        .orElseThrow(() -> new RuntimeException("Menu not found with id: " + toSave.getId())) :
                new Menu();

        menu.setName(toSave.getName());
        menu.setDescription(toSave.getDescription());
        menu.setPrice(toSave.getPrice());
        menu.setStatus(toSave.getStatus());
        menu.setCategory(category);

        var savedMenu = menuRepository.save(menu);

        return toMenuSummarized(savedMenu);
    }

    @Override
    public List<MenuSummarized> findALlMenus() {
        var menuRepository = factory.getMenuRepository();
        return menuRepository.findAll()
                .stream()
                .map(this::toMenuSummarized).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        var menuRepository = factory.getMenuRepository();
        var deletedRows = menuRepository.deleteMenuById(id);
        if (deletedRows == 0) {
            throw new HttpNotFoundException("Menu not found");
        }
    }

    @Override
    public MenuSummarized updateMenuStatus(Long id, MenuStatus newStatus) {
        var menuRepository = factory.getMenuRepository();
        var optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isPresent()) {
            var menu = optionalMenu.get();
            menu.setStatus(newStatus);
            var saved = menuRepository.save(menu);
            return toMenuSummarized(saved);
        } else {
            throw new HttpNotFoundException("Menu not found");
        }
    }

    private MenuSummarized toMenuSummarized(Menu menu) {
        return new MenuSummarized(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getCategory() != null ? menu.getCategory().getId() : null,
                menu.getCreatedAt(),
                menu.getUpdatedAt(),
                menu.getStatus()
        );
    }
}
