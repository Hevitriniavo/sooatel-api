package com.fresh.coding.sooatelapi.dtos.operations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fresh.coding.sooatelapi.enums.OperationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationSummarized {
    private Long id;

    private Long stockId;

    private OperationType type;

    private LocalDateTime date;

    private String description;
}