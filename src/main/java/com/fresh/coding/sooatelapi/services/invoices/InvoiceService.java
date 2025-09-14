package com.fresh.coding.sooatelapi.services.invoices;

import com.fresh.coding.sooatelapi.dtos.UpdateInvoicePaymentStatusRequest;
import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;
import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceLineRequest;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO generateInvoiceIfOrderDelivered(Long orderId);
    void createInvoiceInline(Long invoiceId, List<InvoiceLineRequest> lineRequests, String description);
    InvoiceDTO findInvoiceById(Long invoiceId);

    List<InvoiceDTO> getAllInvoicesOrderedByDate();

    InvoiceDTO updateInvoicePaymentStatus(Long id, UpdateInvoicePaymentStatusRequest paymentStatus);

}
