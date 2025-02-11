package com.fresh.coding.sooatelapi.services.journal;

import com.fresh.coding.sooatelapi.dtos.JournalDto;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
 class JournalServiceImpl implements JournalService {
  private final RepositoryFactory repositoryFactory;

  @Override
  public List<JournalDto> getJournal() {
   var menuOrderRepo = this.repositoryFactory.getMenuOrderRepository();

   List<Object[]> results = menuOrderRepo.findDailyRevenueAndCosts();
   List<JournalDto> journalDtos = new ArrayList<>();

   for (Object[] result : results) {
      var date = (Date) result[0];
      var dailyRevenue = (Double) result[1];
      var dailyIngredientCost = (Double) result[2];
      var dailyNetProfit = (Double) result[3];
      journalDtos.add(new JournalDto(date.toLocalDate(), dailyRevenue, dailyIngredientCost, dailyNetProfit));
   }

   return journalDtos;
  }
}
