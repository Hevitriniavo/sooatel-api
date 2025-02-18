package com.fresh.coding.sooatelapi.dtos.searchs;

import com.fresh.coding.sooatelapi.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationSearch implements Serializable {
    private Long stockId;

    private OperationType type;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
