package com.fresh.coding.sooatelapi.services.auth;

import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import com.fresh.coding.sooatelapi.dtos.users.CreateUser;
import lombok.NonNull;

@FunctionalInterface
public interface SignUpService {
    AuthToken signUp(@NonNull CreateUser user);
}
