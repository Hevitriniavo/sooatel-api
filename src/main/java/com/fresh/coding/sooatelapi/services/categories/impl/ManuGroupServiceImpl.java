package com.fresh.coding.sooatelapi.services.categories.impl;

import com.fresh.coding.sooatelapi.dtos.categories.MenuGroupSummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;
import com.fresh.coding.sooatelapi.entities.MenuGroup;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.EntityService;
import com.fresh.coding.sooatelapi.services.categories.MenuGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManuGroupServiceImpl implements MenuGroupService {
    private final RepositoryFactory factory;
    private final EntityService entityService;

    @Override
    public MenuGroupSummarized save(SaveCategory toSave) {
        var categoryRepository = factory.getCategoryRepository();
        MenuGroup category;
        if (toSave.getId() != null) {
            category = categoryRepository.findById(toSave.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + toSave.getId()));
            category.setName(toSave.getName());
        } else {
            category = MenuGroup.builder()
                    .name(toSave.getName())
                    .build();
        }

        var savedCategory = categoryRepository.save(category);

        return toSummarized(savedCategory);
    }

    @Override
    public List<MenuGroupSummarized> findAllCategories() {
        var categoryRepository = factory.getCategoryRepository();
        return categoryRepository.findAll()
                .stream().map(this::toSummarized)
                .collect(Collectors.toList());
    }

    @Override
    public Paginate<List<MenuGroupSummarized>> findAllCategories(CategorySearch categorySearch, int page, int size) {
        return entityService.findAllCategories(categorySearch, page, size);
    }

    @Override
    public void deleteById(Long id) {
        var categoryRepository = factory.getCategoryRepository();

        if (!categoryRepository.existsById(id)) {
            log.error("Category not found with id: {}", id);
            throw new HttpNotFoundException("Category not found with id: " + id);
        }

        int deletedMenusCount = categoryRepository.deleteMenusByCategoryId(id);
        if (deletedMenusCount > 0) {
            log.info("{} menus deleted for category id: {}", deletedMenusCount, id);
        }

        int deletedCategoriesCount = categoryRepository.deleteCategoryById(id);
        if (deletedCategoriesCount > 0) {
            log.info("{} category deleted with id: {}", deletedCategoriesCount, id);
        }
    }

    private MenuGroupSummarized toSummarized(MenuGroup category){
        return new MenuGroupSummarized(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
