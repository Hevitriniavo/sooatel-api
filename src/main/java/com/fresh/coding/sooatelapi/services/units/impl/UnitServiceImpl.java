package com.fresh.coding.sooatelapi.services.units.impl;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.UnitSearch;
import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.dtos.unit.UpdateUnit;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.EntityService;
import com.fresh.coding.sooatelapi.services.units.UnitService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final RepositoryFactory factory;
    private final EntityService entityService;

    @Override
    public UnitSummarized create(@NonNull CreateUnit toCreate) {
        var unitRepository = factory.getUnitRepository();
        var unit = new Unit();
        BeanUtils.copyProperties(toCreate, unit);
        unit = unitRepository.save(unit);
        return createUnitSummarized(unit);
    }

    @Override
    public Paginate<List<UnitSummarized>> getAllUnits(UnitSearch search, int page, int size) {
        return entityService.getAllUnits(search, page, size);
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
        var ingredientRepository = factory.getIngredientRepository();

        if (!unitRepository.existsById(id)) {
            throw new HttpNotFoundException("Unit with ID " + id + " not found.");
        }

        var updatedCount = ingredientRepository.setUnitToNullByUnitId(id);

        System.out.println("Updated " + updatedCount + " ingredients to detach from Unit ID " + id);

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
