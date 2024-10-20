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
public class Stock extends Model {

    @Column(nullable = false)
    private Double quantity;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(nullable = false, unique = true)
    private Ingredient ingredient;

    @Builder.Default
    @OneToMany(mappedBy = "stock", orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();
}
