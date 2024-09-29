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

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Ingredient ingredient;

    @Builder.Default
    @OneToMany(mappedBy = "stock")
    private List<Operation> operations = new ArrayList<>();
}
