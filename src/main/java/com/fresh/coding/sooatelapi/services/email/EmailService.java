package com.fresh.coding.sooatelapi.services.email;

import com.fresh.coding.sooatelapi.dtos.EmailDto;


public interface EmailService {
    void sendEmail(EmailDto emailDto);
}
