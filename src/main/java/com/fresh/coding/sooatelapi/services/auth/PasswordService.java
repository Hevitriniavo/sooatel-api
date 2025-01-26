package com.fresh.coding.sooatelapi.services.auth;


import com.fresh.coding.sooatelapi.dtos.users.ChangePasswordDto;

public interface PasswordService {
    String changePassword( ChangePasswordDto changePasswordDto);
}
