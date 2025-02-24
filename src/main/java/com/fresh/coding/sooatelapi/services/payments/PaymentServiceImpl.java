package com.fresh.coding.sooatelapi.services.payments;

import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.entities.MenuOrder;
import com.fresh.coding.sooatelapi.entities.Payment;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import com.fresh.coding.sooatelapi.enums.TransactionType;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.cash.CashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PaymentServiceImpl implements PaymentService {
    private final RepositoryFactory repositoryFactory;
    private final CashService cashService;

    @Override
    public List<PaymentSummarized> findAllPayments() {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        return mapToDTOList(paymentRepository.findAll());
    }

    @Transactional
    @Override
    public void deletePayment(Long id) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        paymentRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found"));

        paymentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public PaymentSummarized savePayment(PaymentDTO paymentDTO) {
        Payment payment = mapToEntity(paymentDTO);

        if (paymentDTO.getReservationId() != null) {
            Reservation reservation = repositoryFactory.getReservationRepository()
                    .findById(paymentDTO.getReservationId())
                    .orElseThrow(() -> new HttpNotFoundException("Reservation not found"));
            payment.setReservation(reservation);
            double totalReservationAmount = reservation.calculateTotalReservationAmount();
            payment.setAmount(payment.getAmount() + totalReservationAmount);
        }

        if (paymentDTO.getTableNumbers() != null && !paymentDTO.getTableNumbers().isEmpty()) {
            List<MenuOrder> menuOrders = updateMenuOrdersForTables(paymentDTO.getTableNumbers(), payment);
            payment.setMenuOrders(menuOrders);
        }

        if (paymentDTO.getRoomNumbers() != null && !paymentDTO.getRoomNumbers().isEmpty()) {
            List<MenuOrder> menuOrders = updateMenuOrdersForRooms(paymentDTO.getRoomNumbers(), payment);
            payment.setMenuOrders(menuOrders);
        }

        double totalAmount = calculateTotalPrice(payment);
        payment.setAmount(payment.getAmount() + totalAmount);

        var paymentRepository = repositoryFactory.getPaymentRepository();
        payment = paymentRepository.save(payment);

        if (paymentDTO.getStatus().equals(PaymentStatus.PAID)){
            var cash = CashDTO.builder()
                    .amount(payment.getAmount())
                    .transactionType(TransactionType.MENU_SALE_DEPOSIT)
                    .modeOfTransaction(payment.getPaymentMethod())
                    .description(payment.getDescription())
                    .build();
            cashService.processCashTransaction(cash);
        }

        return mapToDTO(payment);
    }

    private List<MenuOrder> updateMenuOrdersForTables(List<Integer> tableNumbers, Payment payment) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        List<MenuOrder> menuOrders = menuOrderRepository.findAllByTableNumberIn(tableNumbers);
        menuOrders.forEach(menuOrder -> {
            menuOrder.setPayment(payment);
            payment.getMenuOrders().add(menuOrder);
        });
        return menuOrderRepository.saveAll(menuOrders);
    }

    private List<MenuOrder> updateMenuOrdersForRooms(List<Integer> roomNumbers, Payment payment) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        List<MenuOrder> menuOrders = menuOrderRepository.findAllByRoomRoomNumberIn(roomNumbers);
        menuOrders.forEach(menuOrder -> {
            menuOrder.setPayment(payment);
            payment.getMenuOrders().add(menuOrder);
        });
        return menuOrderRepository.saveAll(menuOrders);
    }


    @Override
    public PaymentSummarized updatePaymentStatus(Long id, PaymentStatus status) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        Payment payment = paymentRepository.findById(id).orElseThrow(() ->
                new HttpNotFoundException("Payment not found with ID: " + id));
        payment.setStatus(status);
        paymentRepository.save(payment);

            var cash = CashDTO.builder()
                    .amount(payment.getAmount())
                    .transactionType(status.equals(PaymentStatus.PAID) ? TransactionType.MENU_SALE_DEPOSIT : TransactionType.INGREDIENT_PURCHASE)
                    .modeOfTransaction(payment.getPaymentMethod())
                    .description(payment.getDescription())
                    .build();

            cashService.processCashTransaction(cash);

        return mapToDTO(payment);
    }


    @Override
    public PaymentSummarized updatePaymentMethod(Long id, PaymentMethod method) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        Payment payment = paymentRepository.findById(id).orElseThrow(() ->
                new HttpNotFoundException("Payment not found with ID: " + id));
        payment.setPaymentMethod(method);
        paymentRepository.save(payment);
        return mapToDTO(payment);
    }


    private PaymentSummarized mapToDTO(Payment payment) {
        return new PaymentSummarized(
                payment.getId(),
                payment.getReservation() != null ? payment.getReservation().getId() : null,
                payment.getPaymentDate(),
                payment.getUpdatedAt(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getDescription()
        );
    }

    private List<PaymentSummarized> mapToDTOList(List<Payment> payments) {
        return payments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Payment mapToEntity(PaymentDTO paymentDTO) {
        return Payment.builder()
                .paymentDate(LocalDateTime.now())
                .amount(0.0)
                .paymentMethod(paymentDTO.getPaymentMethod())
                .status(paymentDTO.getStatus())
                .description(paymentDTO.getDescription())
                .build();
    }

    private double calculateTotalPrice(Payment payment) {
        return payment.getMenuOrders().stream()
                .mapToDouble(MenuOrder::getCost)
                .sum();
    }


}
