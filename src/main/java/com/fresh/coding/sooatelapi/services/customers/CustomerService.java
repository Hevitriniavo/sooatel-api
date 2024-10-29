package com.fresh.coding.sooatelapi.services.customers;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerCreateDTO;
import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;

import java.util.List;

public interface CustomerService {
    Paginate<List<CustomerDTO>> findAllCustomers(int page, int size);
    List<CustomerDTO> findAllCustomers();
    CustomerDTO findCustomerById(Long id);
    CustomerDTO createCustomer(CustomerCreateDTO customerCreateDTO);
    CustomerDTO updateCustomer(Long id, CustomerCreateDTO customerCreateDTO);
    void deleteCustomer(Long id);
}
