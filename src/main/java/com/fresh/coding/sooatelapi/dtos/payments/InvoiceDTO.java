package com.fresh.coding.sooatelapi.dtos.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO {
    private PaymentSummarized payment;
    @Builder.Default
    private List<MenuOrderSummarized> orders = new ArrayList<>();
}
