package com.fresh.coding.sooatelapi.services.menus.impl;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenusWithCategorySummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuSearch;
import com.fresh.coding.sooatelapi.entities.Category;
import com.fresh.coding.sooatelapi.entities.Menu;
import com.fresh.coding.sooatelapi.services.menus.SearchMenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchMenuServiceImpl implements SearchMenuService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Paginate<List<MenusWithCategorySummarized>> findAllMenus(MenuSearch menuSearch, int page, int size) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Category.class);
        var root = query.from(Category.class);

        root.fetch("menus", JoinType.LEFT);

        var predicates = buildPredicates(menuSearch, builder, root);
        query.where(predicates.toArray(new Predicate[0]));

        query.select(root).distinct(true);

        var typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        var categories = typedQuery.getResultList();
        long totalItems = getTotalCount(menuSearch);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        var hasPrevious = page > 0;
        var hasNext = page < totalPages - 1;

        return new Paginate<>(toSummarized(categories), new PageInfo(
                hasNext,
                hasPrevious,
                totalPages,
                page,
                (int) totalItems
        ));
    }




    private List<MenusWithCategorySummarized> toSummarized(List<Category> categories) {
        return categories.stream().map(category -> new MenusWithCategorySummarized(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getMenus().stream().map(menu -> new MenuSummarized(
                        menu.getId(),
                        menu.getName(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getCategory() != null ? menu.getCategory().getId() : null,
                        menu.getCreatedAt(),
                        menu.getUpdatedAt()
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
    public long getTotalCount(MenuSearch menuSearch) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(Category.class);

        root.join("menus", JoinType.LEFT);

        var predicates = buildPredicates(menuSearch, builder, root);

        query.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }


    private List<Predicate> buildPredicates(MenuSearch menuSearch, CriteriaBuilder builder, Root<Category> root) {
        var predicates = new ArrayList<Predicate>();

        if (menuSearch.getCategoryName() != null && !menuSearch.getCategoryName().isBlank()) {
            predicates.add(builder.like(
                    builder.lower(root.get("name")),
                    "%" + menuSearch.getCategoryName().toLowerCase() + "%"
            ));
        }

        if (menuSearch.getMenuName() != null && !menuSearch.getMenuName().isBlank()) {
            Join<Category, Menu> menuJoin = root.join("menus", JoinType.LEFT);
            predicates.add(builder.like(
                    builder.lower(menuJoin.get("name")),
                    "%" + menuSearch.getMenuName().toLowerCase() + "%"
            ));
        }

        if (menuSearch.getPriceMin() != null) {
            Join<Category, Menu> menuJoin = root.join("menus", JoinType.LEFT);
            predicates.add(builder.greaterThanOrEqualTo(menuJoin.get("price"), menuSearch.getPriceMin()));
        }

        if (menuSearch.getPriceMax() != null) {
            Join<Category, Menu> menuJoin = root.join("menus", JoinType.LEFT);
            predicates.add(builder.lessThanOrEqualTo(menuJoin.get("price"), menuSearch.getPriceMax()));
        }

        return predicates;
    }

}
