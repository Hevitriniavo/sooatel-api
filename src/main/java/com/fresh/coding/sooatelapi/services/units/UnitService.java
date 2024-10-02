package com.fresh.coding.sooatelapi.services.units;

import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.UnitSearch;
import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.dtos.unit.UpdateUnit;
import lombok.NonNull;

import java.util.List;


public interface UnitService {
    UnitSummarized create(@NonNull CreateUnit toCreate);

    Paginate<List<UnitSummarized>> getAllUnits(UnitSearch unitSearch, int page, int size);
    List<UnitSummarized> getAllUnits();

    UnitSummarized update(@NonNull UpdateUnit toUpdate);

    void delete(Long id);
}
