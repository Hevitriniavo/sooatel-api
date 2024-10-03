package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class CategorySearch implements Serializable {
    private String name;
}
