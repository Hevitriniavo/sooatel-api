package com.fresh.coding.sooatelapi.entities;

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
public class TableEntity extends Model  implements Serializable {

    @Column(nullable = false, unique = true)
    private Long number;

    @Column(nullable = false)
    private Long capacity;

    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<SessionOccupation> sessionOccupations = new ArrayList<>();

    @OneToMany(mappedBy = "table")
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}
