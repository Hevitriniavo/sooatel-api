package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase extends Model  implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double cost;

    @Column(columnDefinition = "TEXT")
    private String description;
}
