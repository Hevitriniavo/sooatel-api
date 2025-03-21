package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Unit extends Model implements Serializable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String abbreviation;

    @Builder.Default
    @OneToMany(mappedBy = "unit", orphanRemoval = true)
    @ToString.Exclude
    private List<Ingredient> ingredients = new ArrayList<>();

}
