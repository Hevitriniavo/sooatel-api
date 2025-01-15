package com.fresh.coding.sooatelapi.services.payments;

import com.fresh.coding.sooatelapi.dtos.payments.InvoiceDTO;

public interface InvoiceService {
    InvoiceDTO getInvoice(Long paymentId);
}
