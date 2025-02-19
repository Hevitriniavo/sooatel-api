package com.fresh.coding.sooatelapi.dtos.payments;


import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PaymentMethodTotalAmountDTO implements Serializable {

    private PaymentMethod paymentMethod;
    private Double totalAmount;
}
