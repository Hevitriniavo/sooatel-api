package com.fresh.coding.sooatelapi.entities;

import com.fresh.coding.sooatelapi.enums.MenuStatus;
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
public class Menu extends Model implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MenuGroup menuGroup;

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
    private List<OrderLine> orderLines = new ArrayList<>();


    @OneToMany(mappedBy = "menu")
    @Builder.Default
    @ToString.Exclude
    private List<InvoiceLine> invoiceLines = new ArrayList<>();
}
