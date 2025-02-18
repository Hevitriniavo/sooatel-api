package com.fresh.coding.sooatelapi.dtos.payments;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSummarized implements Serializable {
    private Long id;
    private Long reservationId;
    private LocalDateTime paymentDate;
    private LocalDateTime updatedAt;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String description;
}
