package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.CreateCustomerRequest;
import com.readingisgood.bookordermanagement.dto.CustomerDTO;
import com.readingisgood.bookordermanagement.model.Customer;
import com.readingisgood.bookordermanagement.repository.CustomerRepository;
import com.readingisgood.bookordermanagement.service.CustomerService;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
   // private final OrderService orderService;

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest createCustomerRequest){

        if(customerRepository.findByEmail(createCustomerRequest.getEmail()).isPresent()){
            log.info("Existing Customer for Email={}",createCustomerRequest.getEmail());
            return null;
        }

        Customer customer = Customer.builder().id(sequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME)).
                name(createCustomerRequest.getName()).email(createCustomerRequest.getEmail()).gsm(createCustomerRequest.getGsm())
                .surname(createCustomerRequest.getSurname()).address(createCustomerRequest.getAddress()).createdDate(Date.from(Instant.now())).build();
        log.info("Success Create");

        customerRepository.save(customer);
        return CustomerDTO.fromCustomer(customer);

    }

    public Customer findCustomerById(Long customerId){

       Optional<Customer> customerOptional = customerRepository.findById(customerId);

       if(customerOptional.isEmpty()){
           log.info("Customer Not Found ,id={}",customerId);
           return  null;
       }else{
           return customerOptional.get();
       }
    }




}
