package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private SessionOccupation sessionOccupation;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime issuedAt = LocalDateTime.now();

    @Column(nullable = false)
    private Long totalAmount;

    @Column
    private Long amountPaid;

    @Enumerated(EnumType.STRING)
    @Column
    private PaymentMethod paymentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Order order;

    @Column
    @Builder.Default
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<InvoiceLine> lines = new ArrayList<>();

}
