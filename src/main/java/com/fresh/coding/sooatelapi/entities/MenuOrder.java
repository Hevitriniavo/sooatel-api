package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "menuOrder")
    @Builder.Default
    private List<EmployeeOrderService> orderServices = new ArrayList<>();
}
