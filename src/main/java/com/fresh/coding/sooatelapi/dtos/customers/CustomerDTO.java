package com.fresh.coding.sooatelapi.dtos.customers;


import com.fresh.coding.sooatelapi.entities.Customer;
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

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
    }
}
