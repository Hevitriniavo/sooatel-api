package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends Model implements Serializable {

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;

    @ManyToOne
    @JoinColumn
    private TableEntity table;

    @Column(nullable = false)
    private LocalDateTime reservationStart;

    @Column(nullable = false)
    private LocalDateTime reservationEnd;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(columnDefinition = "TEXT")
    private String description;
}
