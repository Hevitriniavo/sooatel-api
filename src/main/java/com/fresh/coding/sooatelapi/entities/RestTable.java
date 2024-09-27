package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestTable extends Model {

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status;


    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}
