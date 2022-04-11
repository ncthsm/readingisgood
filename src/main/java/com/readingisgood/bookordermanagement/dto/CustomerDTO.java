package com.readingisgood.bookordermanagement.dto;

import com.readingisgood.bookordermanagement.model.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomerDTO {

    private String name;

    private String surname;

    private String email;

    private String gsm;

    private String address;

    private long id;

    private Date createdDate;

    public static CustomerDTO fromCustomer(Customer customer) {

        return CustomerDTO.builder()
                .email(customer.getEmail()).name(customer.getName())
                .surname(customer.getSurname()).gsm(customer.getGsm())
                .address(customer.getAddress()).createdDate(customer.getCreatedDate())
                .id(customer.getId()).build();
    }

}
