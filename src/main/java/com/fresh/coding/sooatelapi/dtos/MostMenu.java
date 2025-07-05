package com.fresh.coding.sooatelapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class MostMenu {
    private Long id;
    private String name;
    private String description;
    private Long price;
    @JsonIgnore
    private Long categoryId;
    @JsonIgnore
    private String categoryName;
    private Long quantityMenuByName;
}



