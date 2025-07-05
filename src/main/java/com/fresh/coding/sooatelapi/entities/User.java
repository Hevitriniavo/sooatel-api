package com.fresh.coding.sooatelapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Setter
@Builder
public class User extends Model implements UserDetails, Serializable {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Getter
    private String password;

    @Column(nullable = false, unique = true)
    @Getter
    private String email;

    @Column(columnDefinition = "TEXT")
    @Getter
    private String token;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email != null ? email : username;
    }

    @Transient
    public String getName() {
        return username;
    }

}
