package com.fresh.coding.sooatelapi.services.users.impl;

import com.fresh.coding.sooatelapi.dtos.users.UserSummarized;
import com.fresh.coding.sooatelapi.entities.User;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RepositoryFactory repository;


    @Override
    @SneakyThrows(UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String username) {
        var user = repository.getUserRepository().findByEmailOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail()!= null ? user.getEmail() : user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public List<UserSummarized> findAll() {
        var userRepo = this.repository.getUserRepository();
        var dbUsers = userRepo.findAll();
        return dbUsers.stream().map(this::toSummarized)
                .collect(Collectors.toList());
    }

    @Override
    public UserSummarized deleteById(Long id) {
        var userRepo = repository.getUserRepository();
        var user = userRepo.findById(id).orElseThrow(
                () -> new HttpNotFoundException("User not found with Id " + id)
        );
        userRepo.deleteById(id);
        return toSummarized(user);
    }


    public UserSummarized toSummarized(User user) {
        if (user == null) {
            return null;
        }
        return new UserSummarized(
                user.getId(),
                user.getEmail(),
                null,
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
