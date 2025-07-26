package com.fresh.coding.sooatelapi.services.invoices;

import com.fresh.coding.sooatelapi.dtos.UpdateInvoicePaymentStatusRequest;
import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO generateInvoiceIfOrderDelivered(Long orderId);

    InvoiceDTO findInvoiceById(Long invoiceId);

    List<InvoiceDTO> getAllInvoicesOrderedByDate();

    InvoiceDTO updateInvoicePaymentStatus(Long id, UpdateInvoicePaymentStatusRequest paymentStatus);

}
