package com.fresh.coding.sooatelapi.dtos.operations;

import com.fresh.coding.sooatelapi.enums.OperationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationSummarized {
    private Long id;

    private Long stockId;

    private OperationType type;

    private LocalDateTime date;

    private String description;
}
