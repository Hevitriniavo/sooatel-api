package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;
import com.fresh.coding.sooatelapi.dtos.searchs.UnitSearch;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.entities.Category;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.repositories.CategoryRepository;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.repositories.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final RepositoryFactory factory;

    @SuppressWarnings("unchecked")
    private <T, R> Paginate<List<R>> findEntitiesWithPagination(
            String searchName,
            int page,
            int size,
            JpaRepository<T, Long> repository,
            Function<T, R> summarizer
    ) {
        Page<T> entityPage;

        if (repository instanceof CategoryRepository && searchName != null && !searchName.isBlank()) {
            entityPage = (Page<T>) ((CategoryRepository) repository)
                    .findByNameContainingIgnoreCase(searchName, PageRequest.of(page, size));
        } else if (repository instanceof UnitRepository && searchName != null && !searchName.isBlank()) {
            entityPage = (Page<T>) ((UnitRepository) repository)
                    .findByNameContainingIgnoreCase(searchName, PageRequest.of(page, size));
        } else {
            entityPage = repository.findAll(PageRequest.of(page, size));
        }

        var entitySummaries = entityPage.stream()
                .map(summarizer)
                .collect(Collectors.toList());

        var hasPrevious = entityPage.hasPrevious();
        var totalPages = entityPage.getTotalPages();
        var hasNext = entityPage.hasNext();
        var currentPage = entityPage.getNumber();
        var totalItems = (int) entityPage.getTotalElements();
        var pageInfo = new PageInfo(hasNext, hasPrevious, totalPages, currentPage, totalItems);

        return new Paginate<>(entitySummaries, pageInfo);
    }

    @Override
    public Paginate<List<CategorySummarized>> findAllCategories(CategorySearch categorySearch, int page, int size) {
        var categoryRepository = factory.getCategoryRepository();
        return findEntitiesWithPagination(
                categorySearch.getName(),
                page,
                size,
                categoryRepository,
                this::toSummarized
        );
    }

    @Override
    public Paginate<List<UnitSummarized>> getAllUnits(UnitSearch search, int page, int size) {
        var unitRepository = factory.getUnitRepository();
        return findEntitiesWithPagination(
                search.getName(),
                page,
                size,
                unitRepository,
                this::createUnitSummarized
        );
    }

    private CategorySummarized toSummarized(Category category) {
        return new CategorySummarized(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    private UnitSummarized createUnitSummarized(Unit unit) {
        return new UnitSummarized(
                unit.getId(),
                unit.getName(),
                unit.getAbbreviation(),
                unit.getCreatedAt(),
                unit.getUpdatedAt()
        );
    }
}
