package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends Model implements Serializable {

    @ManyToOne
    @JoinColumn
    private Reservation reservation;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;


}
