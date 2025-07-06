package com.fresh.coding.sooatelapi.services.invoices;

import com.fresh.coding.sooatelapi.dtos.invoices.InvoiceDTO;
import com.fresh.coding.sooatelapi.entities.Invoice;
import com.fresh.coding.sooatelapi.entities.InvoiceLine;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    @Override
    public InvoiceDTO generateInvoiceIfOrderDelivered(Long orderId) {
        var orderRepository = repositoryFactory.getMenuOrderRepository();
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFoundException("Aucun ordre trouvé avec l'ID: " + orderId));

        if ( order.getSessionOccupation() == null) {
            throw new IllegalArgumentException("L'ordre n'est pas associé à une session d'occupation");
        }
        var invoiceRepository = repositoryFactory.getInvoiceRepository();

        if (invoiceRepository.existsBySessionOccupation(order.getSessionOccupation())) {
            throw new IllegalStateException("Une facture existe déjà pour cette session d'occupation");
        }
        var session = order.getSessionOccupation();
        if (session.getEndedAt() == null) {
            session.setEndedAt(LocalDateTime.now());
            repositoryFactory.getSessionOccupationRepository().save(session);
        }
        var invoice = Invoice.builder()
                .customer(order.getCustomer())
                .sessionOccupation(order.getSessionOccupation())
                .issuedAt(LocalDateTime.now())
                .paymentStatus(PaymentStatus.UNPAID)
                .paymentDate(null)
                .description("Facture automatique pour la commande #" + order.getId())
                .build();

        List<InvoiceLine> lines = new ArrayList<>();
        var total = 0L;
        for (var ol : order.getOrderLines()) {
            var totalLinePrice = ol.getQuantity() * ol.getUnitPrice();
            var line = InvoiceLine.builder()
                    .invoice(invoice)
                    .menu(ol.getMenu())
                    .quantity(ol.getQuantity())
                    .unitPrice(ol.getUnitPrice())
                    .totalPrice(totalLinePrice)
                    .build();

            lines.add(line);
            total += totalLinePrice;
        }
        invoice.setLines(lines);
        invoice.setTotalAmount(total);
        return toDto(invoiceRepository.save(invoice));
    }

    public InvoiceDTO toDto(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .customerId(invoice.getCustomer() != null ? invoice.getCustomer().getId() : null)
                .sessionOccupationId(invoice.getSessionOccupation() != null ? invoice.getSessionOccupation().getId() : null)
                .issuedAt(invoice.getIssuedAt())
                .totalAmount(invoice.getTotalAmount())
                .amountPaid(invoice.getAmountPaid())
                .paymentMethod(invoice.getPaymentMethod())
                .paymentDate(invoice.getPaymentDate())
                .paymentStatus(invoice.getPaymentStatus())
                .description(invoice.getDescription())
                .lines(invoice.getLines().stream().map(line -> InvoiceDTO.InvoiceLineDTO.builder()
                                .id(line.getId())
                                .menuId(line.getMenu() != null ? line.getMenu().getId() : null)
                                .quantity(line.getQuantity())
                                .unitPrice(line.getUnitPrice())
                                .totalPrice(line.getTotalPrice())
                                .build())
                        .toList())
                .build();
    }

}
