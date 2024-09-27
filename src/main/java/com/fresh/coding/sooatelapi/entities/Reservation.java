package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.ReservationStatus;
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
public class Reservation extends Model {

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;

    @ManyToOne
    @JoinColumn
    private RestTable table;

    @Column
    private LocalDateTime reservationStart;

    @Column
    private LocalDateTime reservationEnd;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "reservation")
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

}
