package com.fresh.coding.sooatelapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {

    @GetMapping
    public Map<String, String> ping(){
        return Map.of("message", "Ping");
    }
}
