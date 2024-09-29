package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock extends Model {

    @Column(nullable = false)
    private Double quantity;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Ingredient ingredient;
}
