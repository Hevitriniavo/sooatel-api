package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unit extends Model {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String abbreviation;

    @Builder.Default
    @OneToMany(mappedBy = "unit")
    private List<Ingredient> ingredients = new ArrayList<>();
}
