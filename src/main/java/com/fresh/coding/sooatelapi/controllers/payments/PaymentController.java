package com.fresh.coding.sooatelapi.controllers.payments;

import com.fresh.coding.sooatelapi.dtos.payments.PaymentDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import com.fresh.coding.sooatelapi.services.payments.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Validated
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/status")
    public List<PaymentStatus> getPaymentStatus() {
        return Arrays.asList(PaymentStatus.values());
    }

    @GetMapping("/method")
    public List<PaymentMethod> getPaymentMethod() {
        return Arrays.asList(PaymentMethod.values());
    }

    @GetMapping
    public List<PaymentSummarized> getPayments() {
        return paymentService.findAllPayments();
    }

    @PostMapping
    public PaymentSummarized savePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        return paymentService.savePayment(paymentDTO);
    }

    @PutMapping("/update/status/{id}")
    public PaymentSummarized updatePaymentStatus(@PathVariable Long id, @RequestBody PaymentStatus status) {
        return paymentService.updatePaymentStatus(id, status);
    }

    @PutMapping("/update/method/{id}")
    public PaymentSummarized updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethod method) {
        return paymentService.updatePaymentMethod(id, method);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}
