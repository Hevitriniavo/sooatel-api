package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.OperationType;
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
public class Operation  implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double quantity;

    @Column(columnDefinition = "TEXT")
    private String description;
}
