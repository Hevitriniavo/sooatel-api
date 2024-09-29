package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient extends Model {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Unit unit;

    @OneToOne(mappedBy = "ingredient")
    private Stock stock;

    @Builder.Default
    @OneToMany(mappedBy = "ingredient")
    private List<MenuIngredient> menuIngredients = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "ingredient")
    private List<Purchase> purchases = new ArrayList<>();
}
