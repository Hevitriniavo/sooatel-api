package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.dtos.categories.CategoryMostSummarized;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MostSoldMenuDTO {
    private CategoryMostSummarized category;
    private List<MostMenu> menus = new ArrayList<>();
}
