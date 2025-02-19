package com.fresh.coding.sooatelapi.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ProfitLossDTO implements Serializable {
    private Double totalReceived;
    private Double totalSpent;
    private Double profitLoss;
}
