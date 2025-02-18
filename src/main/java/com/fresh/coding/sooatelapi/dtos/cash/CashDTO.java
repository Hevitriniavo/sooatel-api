package com.fresh.coding.sooatelapi.dtos.cash;


import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.TransactionType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashDTO implements Serializable {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType transactionType;

    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod modeOfTransaction;

    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

}
