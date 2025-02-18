package com.fresh.coding.sooatelapi.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Cash implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    private LocalDateTime lastUpdated;

    @PrePersist
    protected void beforeUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}
