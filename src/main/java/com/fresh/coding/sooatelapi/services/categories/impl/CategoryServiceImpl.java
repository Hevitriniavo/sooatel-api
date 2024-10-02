package com.fresh.coding.sooatelapi.services.categories.impl;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.entities.Category;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.categories.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final RepositoryFactory factory;

    @Override
    public CategorySummarized save(SaveCategory toSave) {
        var categoryRepository = factory.getCategoryRepository();
        Category category;
        if (toSave.getId() != null) {
            category = categoryRepository.findById(toSave.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + toSave.getId()));
            category.setName(toSave.getName());
        } else {
            category = Category.builder()
                    .name(toSave.getName())
                    .build();
        }

        var savedCategory = categoryRepository.save(category);

        return toSummarized(savedCategory);
    }

    private CategorySummarized toSummarized(Category category){
        return new CategorySummarized(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
