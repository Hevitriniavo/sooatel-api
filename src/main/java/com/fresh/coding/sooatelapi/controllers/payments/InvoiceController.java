package com.fresh.coding.sooatelapi.controllers.payments;

import com.fresh.coding.sooatelapi.dtos.payments.InvoiceDTO;
import com.fresh.coding.sooatelapi.services.payments.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/{paymentId}")
    public InvoiceDTO getInvoice(
            @PathVariable Long paymentId
    ) {
        return invoiceService.getInvoice(paymentId);
    }

}
