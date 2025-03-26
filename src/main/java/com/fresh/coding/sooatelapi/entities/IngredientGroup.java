package com.fresh.coding.sooatelapi.entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class IngredientGroup extends Model implements Serializable {

    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "group", orphanRemoval = true)
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();
}
