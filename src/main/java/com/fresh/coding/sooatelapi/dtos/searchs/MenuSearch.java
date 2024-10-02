package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuSearch implements Serializable {
    private String categoryName;
    private String menuName;
    private Double priceMax;
    private Double priceMin;
}
