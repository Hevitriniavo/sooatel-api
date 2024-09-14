package com.fresh.coding.sooatelapi.services.auth;

import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import com.fresh.coding.sooatelapi.dtos.users.SignInUser;
import lombok.NonNull;

@FunctionalInterface
public interface SignInService {
    @NonNull
    AuthToken signIn(@NonNull SignInUser user);
}

