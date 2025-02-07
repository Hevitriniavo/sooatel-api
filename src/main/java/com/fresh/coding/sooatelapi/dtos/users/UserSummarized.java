package com.fresh.coding.sooatelapi.dtos.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserSummarized extends UserBase implements Serializable {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserSummarized(
            Long id,
            String email,
            String password,
            String username,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        super(email, password);
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
