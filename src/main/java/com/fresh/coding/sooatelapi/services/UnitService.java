package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import lombok.NonNull;

import java.util.List;


public interface UnitService {
    UnitSummarized create(@NonNull CreateUnit toCreate);

    List<UnitSummarized> getAllUnits();
}
