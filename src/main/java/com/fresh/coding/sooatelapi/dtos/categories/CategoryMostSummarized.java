package com.fresh.coding.sooatelapi.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CategoryMostSummarized implements Serializable {
    private Long id;
    private String name;
}
