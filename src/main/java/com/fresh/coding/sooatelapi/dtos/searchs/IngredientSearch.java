package com.fresh.coding.sooatelapi.dtos.searchs;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class IngredientSearch implements Serializable {
    private String ingredientName;
    private String unitName;
}
