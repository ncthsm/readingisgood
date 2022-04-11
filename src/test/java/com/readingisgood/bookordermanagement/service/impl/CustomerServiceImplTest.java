package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.CreateCustomerRequest;
import com.readingisgood.bookordermanagement.dto.CustomerDTO;
import com.readingisgood.bookordermanagement.model.Customer;
import com.readingisgood.bookordermanagement.repository.CustomerRepository;
import com.readingisgood.bookordermanagement.service.OrderService;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    @Mock
    SequenceGeneratorService sequenceGeneratorService;


    public final static String email = "test@test.com";
    public final static String name = "TestName";
    public final static String surName = "TestSurname";
    public final static String gsm = "+905555555555";
    public final static String address = "TestAddress";

    @Test
    void createCustomer() {

        CreateCustomerRequest request = new CreateCustomerRequest(name,surName,email,gsm,address);

        Customer customer = Customer.builder()
                .email(email)
                .name(name)
                .surname(surName)
                .gsm(gsm)
                .address(address)
                .build();

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customerRepository.findByEmail(Mockito.any(String.class))).thenReturn(Optional.empty());

        CustomerDTO customerDto = customerService.createCustomer(request);

        assertNotNull(customerDto);
        assertEquals(request.getEmail(), customerDto.getEmail());
        assertEquals(request.getAddress(), customerDto.getAddress());
        assertEquals(request.getName(), customerDto.getName());

    }

    @Test
    void findCustomerTest(){

        Long customerId = 1L;

        Customer customer = Customer.builder()
                .email(email)
                .name(name)
                .surname(surName)
                .gsm(gsm)
                .address(address)
                .build();

        Optional<Customer> customerOptional = Optional.of(customer);

        Mockito.when(customerRepository.findById(customerId)).thenReturn(customerOptional);

        Customer customerById = customerService.findCustomerById(customerId);

        assertNotNull(customerById);


    }

}