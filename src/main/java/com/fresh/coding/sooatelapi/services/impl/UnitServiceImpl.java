package com.fresh.coding.sooatelapi.services.impl;

import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.UnitService;
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

    @Override
    public UnitSummarized create(@NonNull CreateUnit toCreate) {
        var unitRepository = factory.getUnitRepository();
        var unit = new Unit();
        BeanUtils.copyProperties(toCreate, unit);
        unit = unitRepository.save(unit);
        return createUnitSummarized(unit);
    }

    @Override
    public List<UnitSummarized> getAllUnits() {
        var unitRepository = factory.getUnitRepository();
        var units = unitRepository.findAll();
        return units.stream()
                .map(this::createUnitSummarized)
                .collect(Collectors.toList());
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
