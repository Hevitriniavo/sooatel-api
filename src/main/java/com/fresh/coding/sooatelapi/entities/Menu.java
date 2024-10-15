package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.MenuStatus;
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
@ToString
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
    @ToString.Exclude
    private List<MenuIngredient> menuIngredients = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status;

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    @ToString.Exclude
    private List<MenuOrder> menuOrders = new ArrayList<>();
}
