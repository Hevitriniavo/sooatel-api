package com.fresh.coding.sooatelapi.dtos.searchs;

import com.fresh.coding.sooatelapi.enums.TableStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TableSearch implements Serializable {
    private Integer tableNumberMax;
    private Integer tableNumberMin;
    private Integer capacityMin;
    private Integer capacityMax;
    private TableStatus status;
}
