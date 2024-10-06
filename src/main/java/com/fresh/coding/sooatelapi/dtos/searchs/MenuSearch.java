package com.fresh.coding.sooatelapi.dtos.searchs;

import com.fresh.coding.sooatelapi.enums.MenuStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuSearch implements Serializable {
    private Long categoryId;
    private String menuName;
    private Double priceMax;
    private Double priceMin;
    private MenuStatus status;
}
