package com.fresh.coding.sooatelapi.services.journal;

import com.fresh.coding.sooatelapi.dtos.JournalDto;

import java.util.List;

public interface JournalService {
    List<JournalDto> getJournal();
}
