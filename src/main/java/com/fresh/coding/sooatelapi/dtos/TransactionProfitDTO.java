package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionProfitDTO implements Serializable {
    private PaymentMethod modeOfTransaction;
    private Double profitOrLoss;
}
