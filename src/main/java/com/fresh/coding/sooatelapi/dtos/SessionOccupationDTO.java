package com.fresh.coding.sooatelapi.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionOccupationDTO {
    private Long id;
    private Long customerId;
    private Long tableId;
    private Long roomId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String description;
    private List<Long> orderIds;
    private List<Long> invoiceIds;
}
