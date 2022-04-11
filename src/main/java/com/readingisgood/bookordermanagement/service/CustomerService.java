package com.readingisgood.bookordermanagement.service;

import com.readingisgood.bookordermanagement.controller.request.CreateCustomerRequest;
import com.readingisgood.bookordermanagement.dto.CustomerDTO;
import com.readingisgood.bookordermanagement.model.Customer;

public interface CustomerService {

    public CustomerDTO createCustomer(CreateCustomerRequest createCustomerRequest);
    public Customer findCustomerById(Long customerId);

}
