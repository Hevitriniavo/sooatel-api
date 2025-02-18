package com.fresh.coding.sooatelapi.dtos.users;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class UpdateUser extends UserBase implements Serializable {

    @NotNull(message = "ID cannot be null")
    @Min(value = 1, message = "ID must be greater than or equal to 1")
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    public UpdateUser(Long id, String email, String password, String username) {
        super(email, password);
        this.id = id;
        this.username = username;
    }
}
