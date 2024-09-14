package com.fresh.coding.sooatelapi.services.users;


import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtHelperService {
    AuthToken generateToken(UserDetails userDetails);
    boolean isValid(String token, UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims, T> fn);
    String extractUsername(String token);
}
