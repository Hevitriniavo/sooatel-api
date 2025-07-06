package com.fresh.coding.sooatelapi.controllers.invoices;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {
    @GetMapping("/payments/method")
    public List<PaymentMethod> getPaymentMethods() {
        return List.of(PaymentMethod.values());
    }
    @GetMapping("/payments/status")
    public List<PaymentStatus> getPaymentStatuses() {
        return List.of(PaymentStatus.values());
    }
}
