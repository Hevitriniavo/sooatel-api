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
public class Category extends Model {

    @Column(nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Menu> menus = new ArrayList<>();
}
