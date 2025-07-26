package com.fresh.coding.sooatelapi.controllers.invoices;

import com.fresh.coding.sooatelapi.dtos.UpdateInvoicePaymentStatusRequest;
import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;
import com.fresh.coding.sooatelapi.services.invoices.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/generate/{orderId}")
    public InvoiceDTO getDetailsInvoices(@PathVariable Long orderId) {
        return invoiceService.generateInvoiceIfOrderDelivered(orderId);
    }

    @GetMapping("/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable Long invoiceId) {
        return invoiceService.findInvoiceById(invoiceId);
    }

    @GetMapping
    public List<InvoiceDTO> getAllInvoicesOrderedByDate() {
        return invoiceService.getAllInvoicesOrderedByDate();
    }

    @PutMapping("/{id}/payment-status")
    public InvoiceDTO updatePaymentStatus (
            @PathVariable Long id,
            @RequestBody UpdateInvoicePaymentStatusRequest request
    ) {
        return invoiceService.updateInvoicePaymentStatus(id, request);
    }

}
