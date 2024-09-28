package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.dtos.unit.UpdateUnit;
import lombok.NonNull;

import java.util.List;


public interface UnitService {
    UnitSummarized create(@NonNull CreateUnit toCreate);

    Paginate<List<UnitSummarized>> getAllUnits(String name, int page, int size);

    UnitSummarized update(@NonNull UpdateUnit toUpdate);

    void delete(Long id);
}
