package com.fresh.coding.sooatelapi.dtos.payments;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO implements Serializable {

    private Long reservationId;

    private List<Integer> tableNumbers;

    private List<Integer> roomNumbers;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
