package com.fresh.coding.sooatelapi.services.users;

import com.fresh.coding.sooatelapi.dtos.users.UserSummarized;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserSummarized> findAll();

    UserSummarized deleteById(Long id);
}
