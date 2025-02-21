package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionProfitDTO implements Serializable {
    private PaymentMethod paymentMethod;
    private Double ingredientLossWithoutReturns;
    private Double ingredientLossWithReturns;
    private Double menuSaleProfitNoManualDeposit;
    private Double menuSaleProfitWithManualDeposit;

    public void setIngredientLossWithReturns(Double purchaseAmount, Double returnAmount) {
        this.ingredientLossWithReturns = purchaseAmount - returnAmount;
    }
}
