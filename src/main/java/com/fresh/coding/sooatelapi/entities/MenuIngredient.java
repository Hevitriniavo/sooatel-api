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
@ToString
public class MenuIngredient extends Model  implements Serializable {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;
}
