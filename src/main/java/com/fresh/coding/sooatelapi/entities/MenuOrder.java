package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuOrder extends Model {

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Menu menu;

    @ManyToOne
    @JoinColumn
    private Room room;

    @ManyToOne
    @JoinColumn
    private RestTable table;

    @Column
    private LocalDateTime orderDate;

    @Column
    private Double quantity;

    @Column(nullable = false)
    private Double cost;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Payment payment;
}
