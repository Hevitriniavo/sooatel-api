package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodProfitDTO {
    private PaymentMethod paymentMethod;
    private Double totalIn;
    private Double totalOut;
    private Double profit;
}
