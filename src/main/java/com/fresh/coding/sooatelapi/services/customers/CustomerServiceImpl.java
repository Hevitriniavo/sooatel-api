package com.fresh.coding.sooatelapi.services.customers;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerCreateDTO;
import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.entities.Customer;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final RepositoryFactory repositoryFactory;

    @Override
    public Paginate<List<CustomerDTO>> findAllCustomers(int page, int size) {
        var customerRepository = repositoryFactory.getCustomerRepository();
        var pageRequest = PageRequest.of(page, size);
        var customerPage = customerRepository.findAll(pageRequest);

        var customers = customerPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        var pageInfo = new PageInfo(
                customerPage.hasNext(),
                customerPage.hasPrevious(),
                customerPage.getTotalPages(),
                customerPage.getNumber(),
                (int) customerPage.getTotalElements()
        );

        return new Paginate<>(customers, pageInfo);
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        var customerRepository = repositoryFactory.getCustomerRepository();
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"));
        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerCreateDTO customerCreateDTO) {
        var customerRepository = repositoryFactory.getCustomerRepository();
        var customer = mapToEntity(customerCreateDTO);
        var savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerCreateDTO customerCreateDTO) {
        var customerRepository = repositoryFactory.getCustomerRepository();
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"));
        customer.setFirstName(customerCreateDTO.getFirstName());
        customer.setLastName(customerCreateDTO.getLastName());
        customer.setPhoneNumber(customerCreateDTO.getPhoneNumber());
        customer.setEmail(customerCreateDTO.getEmail());
        customer.setAddress(customerCreateDTO.getAddress());

        var updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }
    @Override
    public void deleteCustomer(Long id) {
        var customerRepository = repositoryFactory.getCustomerRepository();

        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Customer with ID " + id + " not found"));

        customerRepository.delete(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    private Customer mapToEntity(CustomerCreateDTO customerCreateDTO) {
        return Customer.builder()
                .firstName(customerCreateDTO.getFirstName())
                .lastName(customerCreateDTO.getLastName())
                .phoneNumber(customerCreateDTO.getPhoneNumber())
                .email(customerCreateDTO.getEmail())
                .address(customerCreateDTO.getAddress())
                .build();
    }
}
