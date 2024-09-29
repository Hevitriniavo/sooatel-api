package com.fresh.coding.sooatelapi.services.impl;

import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.dtos.unit.UpdateUnit;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.UnitService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final RepositoryFactory factory;

    @Override
    public UnitSummarized create(@NonNull CreateUnit toCreate) {
        var unitRepository = factory.getUnitRepository();
        var unit = new Unit();
        BeanUtils.copyProperties(toCreate, unit);
        unit = unitRepository.save(unit);
        return createUnitSummarized(unit);
    }

    @Override
    public Paginate<List<UnitSummarized>> getAllUnits(String name, int page, int size) {
        var unitRepository = factory.getUnitRepository();
        Page<Unit> unitPage;
        if (name != null && !name.isBlank() && !name.isEmpty()) {
            unitPage = unitRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page , size));
        } else {
            unitPage = unitRepository.findAll(PageRequest.of(page, size));
        }

        var unitSummaries = unitPage.stream()
                .map(this::createUnitSummarized)
                .collect(Collectors.toList());

        var hasNext = unitPage.hasNext();
        var hasPrevious = unitPage.hasPrevious();
        var totalPages = unitPage.getTotalPages();
        var currentPage = unitPage.getNumber();
        var totalItems = (int) unitPage.getTotalElements();
        var pageInfo = new PageInfo(hasNext, hasPrevious, totalPages, currentPage, totalItems);
        return new Paginate<>(unitSummaries, pageInfo);
    }

    @Override
    public List<UnitSummarized> getAllUnits() {
        var unitRepository = factory.getUnitRepository();
        return unitRepository.findAll().stream()
                .map(this::createUnitSummarized)
                .collect(Collectors.toList());
    }

    @Override
    public UnitSummarized update(@NonNull UpdateUnit toUpdate) {
        var unitRepository = factory.getUnitRepository();
        var existingUnitOpt = unitRepository.findById(toUpdate.getId());

        if (existingUnitOpt.isEmpty()) {
            throw new HttpNotFoundException("Unit with ID " + toUpdate.getId() + " not found.");
        }
        var existingUnit = existingUnitOpt.get();
        BeanUtils.copyProperties(toUpdate, existingUnit, "id");
        existingUnit = unitRepository.save(existingUnit);
        return createUnitSummarized(existingUnit);
    }


    @Override
    public void delete(Long id) {
        var unitRepository = factory.getUnitRepository();
        if (!unitRepository.existsById(id)) {
            throw new HttpNotFoundException("Unit with ID " + id + " not found.");
        }
        unitRepository.deleteById(id);
    }



    private UnitSummarized createUnitSummarized(Unit unit) {
        return new UnitSummarized(
                unit.getId(),
                unit.getName(),
                unit.getAbbreviation(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
