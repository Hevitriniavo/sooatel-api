package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.TableStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestTable extends Model  implements Serializable {

    @Column(nullable = false, unique = true)
    private Integer number;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TableStatus status;


    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Reservation reservation;
}
