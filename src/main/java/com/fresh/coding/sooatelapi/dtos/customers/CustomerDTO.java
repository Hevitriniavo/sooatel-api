package com.fresh.coding.sooatelapi.dtos.customers;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    private Long id;
    private String name;
    private String phoneNumber;
}
