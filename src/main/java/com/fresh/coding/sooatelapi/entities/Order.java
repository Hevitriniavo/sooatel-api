package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends Model implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private TableEntity table;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private SessionOccupation sessionOccupation;

    @Column
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

}
