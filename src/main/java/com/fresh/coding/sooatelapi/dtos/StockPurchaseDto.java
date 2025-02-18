package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StockPurchaseDto implements Serializable {

    @NotNull(message = "Ingredient ID cannot be null")
    private Long ingredientId;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Quantity must be greater than 0")
    private Double quantity;

    @NotNull(message = "Cost cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    private Double cost;

    private PaymentMethod method;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
