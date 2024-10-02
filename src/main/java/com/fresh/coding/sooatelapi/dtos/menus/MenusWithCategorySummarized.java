package com.fresh.coding.sooatelapi.dtos.menus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public  class MenusWithCategorySummarized implements Serializable {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MenuSummarized> menus ;
}
