package com.fresh.coding.sooatelapi.services.auth.impl;

import com.fresh.coding.sooatelapi.dtos.users.ChangePasswordDto;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.auth.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

    private final RepositoryFactory repositoryFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        var userRepo = this.repositoryFactory.getUserRepository();

        var user = userRepo.findByEmailOrUsername(changePasswordDto.getEmail(), changePasswordDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + changePasswordDto.getEmail()));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirmation do not match.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepo.save(user);

        return "Password changed successfully!";
    }
}
