package com.fresh.coding.sooatelapi.services.payments;

import com.fresh.coding.sooatelapi.dtos.payments.PaymentDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;

import java.util.List;

public interface PaymentService {
    PaymentSummarized savePayment(PaymentDTO paymentDTO);

    List<PaymentSummarized> findAllPayments();

    PaymentSummarized updatePaymentStatus(Long id, PaymentStatus status);

    PaymentSummarized updatePaymentMethod(Long id, PaymentMethod method);

    void deletePayment(Long id);
}
