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
public class Menu extends Model {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Builder.Default
    @OneToMany(mappedBy = "menu")
    private List<MenuIngredient> menuIngredients = new ArrayList<>();


    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuOrder> menuOrders = new ArrayList<>();
}
