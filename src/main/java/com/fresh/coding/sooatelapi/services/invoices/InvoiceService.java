package com.fresh.coding.sooatelapi.services.invoices;

import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;

public interface InvoiceService {
    InvoiceDTO generateInvoiceIfOrderDelivered(Long orderId);
}
