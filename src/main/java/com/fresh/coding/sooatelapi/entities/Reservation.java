package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends Model  implements Serializable {

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "reservation", cascade = {CascadeType.ALL},  fetch = FetchType.EAGER)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Builder.Default
    private List<RestTable> tables = new ArrayList<>();

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


    public double calculateTotalReservationAmount() {
        return payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
    }

}
