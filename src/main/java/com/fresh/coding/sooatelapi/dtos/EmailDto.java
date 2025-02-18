package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.entities.OtpCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto  implements Serializable {
    private String to;
    private OtpCode otpCode;
}
