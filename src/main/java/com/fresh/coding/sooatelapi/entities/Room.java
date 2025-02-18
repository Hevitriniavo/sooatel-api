package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.RoomStatus;
import jakarta.persistence.*;
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
public class Room extends Model  implements Serializable {

    @Column(nullable = false, unique = true)
    private Integer roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn
    private Floor floor;


    @OneToMany(mappedBy = "room")
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Reservation reservation;
}
