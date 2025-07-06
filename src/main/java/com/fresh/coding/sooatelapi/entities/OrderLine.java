package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Menu menu;

    @Column
    private Long quantity;

    @Column
    private Long unitPrice;

    @Column
    private Long totalPrice;
}

