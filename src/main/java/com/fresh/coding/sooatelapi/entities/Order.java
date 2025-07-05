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
    private TableEntity table;

    @ManyToOne
    @JoinColumn
    private SessionOccupation sessionOccupation;

    @Column
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderLine> orderLines = new ArrayList<>();
}
