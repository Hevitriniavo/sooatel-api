package com.fresh.coding.sooatelapi.services.auth.impl;

import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import com.fresh.coding.sooatelapi.dtos.users.SignInUser;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.auth.SignInService;
import com.fresh.coding.sooatelapi.services.users.JwtHelperService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final JwtHelperService jwtHelperService;
    private final RepositoryFactory repository;
    private final AuthenticationManager authenticationManager;

    @Override
    public @NonNull AuthToken signIn(@NonNull SignInUser user) {
        var foundUser = repository.getUserRepository()
                .findByEmailOrUsername(user.getEmail(), user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var authToken = jwtHelperService.generateToken(foundUser);
        authToken.setToken(authToken.getToken());
        repository.getUserRepository().save(foundUser);
        return authToken;
    }
}
