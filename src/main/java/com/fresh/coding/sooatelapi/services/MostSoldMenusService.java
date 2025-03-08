package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.MostSoldMenuDTO;

import java.time.LocalDate;
import java.util.List;

public interface  MostSoldMenusService {
    List<MostSoldMenuDTO> findMostSoldMenusByCategory(LocalDate date);
}
