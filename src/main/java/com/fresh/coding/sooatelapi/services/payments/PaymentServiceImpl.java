package com.fresh.coding.sooatelapi.services.payments;

import com.fresh.coding.sooatelapi.dtos.payments.PaymentDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.entities.Payment;
import com.fresh.coding.sooatelapi.entities.Reservation;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    @Override
    public PaymentSummarized savePayment(PaymentDTO paymentDTO) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        var reservationRepository = repositoryFactory.getReservationRepository();
        var reservation = reservationRepository.findById(paymentDTO.getReservationId())
                .orElseThrow(() -> new HttpNotFoundException("Reservation not found"));

        var payment = findOrCreatePayment(paymentDTO);
        updatePaymentFromDTO(paymentDTO, payment, reservation);
        var savedPayment = paymentRepository.save(payment);
        return mapToDto(savedPayment);
    }

    @Override
    public List<PaymentSummarized> findAllPayments() {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        return paymentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentSummarized updatePaymentStatus(Long id, PaymentStatus status) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found"));

        payment.setStatus(status);
        var updatedPayment = paymentRepository.save(payment);
        return mapToDto(updatedPayment);
    }

    @Override
    public PaymentSummarized updatePaymentMethod(Long id, PaymentMethod method) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found"));

        payment.setPaymentMethod(method);
        var updatedPayment = paymentRepository.save(payment);
        return mapToDto(updatedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Payment not found"));
        paymentRepository.delete(payment);
    }

    private Payment findOrCreatePayment(PaymentDTO paymentDTO) {
        var paymentRepository = repositoryFactory.getPaymentRepository();
        return paymentDTO.getId() != null ? paymentRepository.findById(paymentDTO.getId())
                .orElseThrow(() -> new HttpNotFoundException("Payment not found")) : new Payment();
    }

    private void updatePaymentFromDTO(PaymentDTO paymentDTO, Payment payment, Reservation reservation) {
        BeanUtils.copyProperties(paymentDTO, payment, "id", "reservation", "paymentDate", "updatedAt");
        payment.setReservation(reservation);
        payment.setPaymentDate(paymentDTO.getPaymentDate() != null ? paymentDTO.getPaymentDate() : payment.getPaymentDate());
        payment.setUpdatedAt(LocalDateTime.now());
    }

    private PaymentSummarized mapToDto(Payment payment) {
        return new PaymentSummarized(
                payment.getId(),
                payment.getReservation().getId(),
                payment.getPaymentDate(),
                payment.getUpdatedAt(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getDescription()
        );
    }
}
