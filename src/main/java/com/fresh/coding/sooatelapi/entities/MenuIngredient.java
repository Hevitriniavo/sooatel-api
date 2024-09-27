package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuIngredient extends Model {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;
}
