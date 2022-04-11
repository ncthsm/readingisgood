package com.readingisgood.bookordermanagement.repository;

import com.readingisgood.bookordermanagement.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer,Long> {

    Optional<Customer> findByEmail(String email);

}
