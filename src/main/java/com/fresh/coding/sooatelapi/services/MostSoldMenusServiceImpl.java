package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.MostMenu;
import com.fresh.coding.sooatelapi.dtos.MostSoldMenuDTO;
import com.fresh.coding.sooatelapi.dtos.categories.CategoryMostSummarized;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
class MostSoldMenusServiceImpl implements MostSoldMenusService {
    private final RepositoryFactory factory;

    @Override
    public List<MostSoldMenuDTO> findMostSoldMenusByCategory(LocalDate date) {
        var menuOrderRepo = factory.getMenuOrderRepository();
        var mostMenus = menuOrderRepo.findMostSoldMenusByDate(date);
        Map<CategoryMostSummarized, List<MostMenu>> groupedByCategory = mostMenus.stream()
                .collect(Collectors.groupingBy(menu -> new CategoryMostSummarized(menu.getCategoryId(), menu.getCategoryName())));

        return groupedByCategory.entrySet().stream()
                .map(entry -> new MostSoldMenuDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
