package com.fresh.coding.sooatelapi.controllers;


import com.fresh.coding.sooatelapi.dtos.JournalDto;
import com.fresh.coding.sooatelapi.services.journal.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @GetMapping
    public List<JournalDto> getJournal() {
        return journalService.getJournal();
    }

}
