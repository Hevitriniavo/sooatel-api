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
public class Floor extends Model implements Serializable {

    @Column(nullable = false, unique = true)
    private Integer floorNumber;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "floor")
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();
}
