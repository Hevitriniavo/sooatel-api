package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ingredient extends Model implements Serializable  {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn
    private Unit unit;

    @OneToOne(mappedBy = "ingredient", orphanRemoval = true)
    private Stock stock;

    @Builder.Default
    @OneToMany(mappedBy = "ingredient", orphanRemoval = true)
    @ToString.Exclude
    private List<MenuIngredient> menuIngredients = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private IngredientGroup group;
}
