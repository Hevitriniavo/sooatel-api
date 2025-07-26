package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateInvoicePaymentStatusRequest {

    @NotNull(message = "Le statut de paiement est obligatoire")
    private PaymentStatus paymentStatus;

    @Min(value = 0, message = "Le montant payé doit être positif ou nul")
    private Long amountPaid;

    private PaymentMethod paymentMethod;

    @PastOrPresent(message = "La date de paiement ne peut pas être dans le futur")
    private LocalDateTime paymentDate;
}
