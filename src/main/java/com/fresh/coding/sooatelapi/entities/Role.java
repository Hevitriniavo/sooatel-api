package com.fresh.coding.sooatelapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fresh.coding.sooatelapi.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Builder
public class Role extends Model {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    @JsonBackReference
    private List<User> users = new ArrayList<>();

}
