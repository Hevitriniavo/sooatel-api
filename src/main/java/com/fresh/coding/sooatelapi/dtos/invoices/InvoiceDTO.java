package com.fresh.coding.sooatelapi.dtos.invoices;

import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {
    private Long id;
    private Long customerId;
    private Long sessionOccupationId;
    private LocalDateTime issuedAt;
    private Long totalAmount;
    private Long amountPaid;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
    private String description;
    private TableSummarized table;
    private RoomDTO room;
    private List<InvoiceLineDTO> lines;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InvoiceLineDTO {
        private Long id;
        private Long menuId;
        private Long quantity;
        private Long unitPrice;
        private Long totalPrice;
    }
}
