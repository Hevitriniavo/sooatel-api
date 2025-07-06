package com.fresh.coding.sooatelapi.controllers.invoices;

import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;
import com.fresh.coding.sooatelapi.entities.Invoice;
import com.fresh.coding.sooatelapi.services.invoices.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;


    @PostMapping("/generate/{orderId}")
    public InvoiceDTO generateInvoice(@PathVariable Long orderId) {
        return invoiceService.generateInvoiceIfOrderDelivered(orderId);
    }

}
