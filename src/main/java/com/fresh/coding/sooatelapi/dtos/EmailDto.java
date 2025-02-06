package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.entities.OtpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private String to;
    private OtpCode otpCode;
}
