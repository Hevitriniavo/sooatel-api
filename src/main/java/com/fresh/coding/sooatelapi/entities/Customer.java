package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Model  implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}
