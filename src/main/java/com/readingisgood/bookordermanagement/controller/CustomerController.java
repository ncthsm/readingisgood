package com.readingisgood.bookordermanagement.controller;

import com.readingisgood.bookordermanagement.controller.request.CreateCustomerRequest;
import com.readingisgood.bookordermanagement.dto.CustomerDTO;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.model.Customer;
import com.readingisgood.bookordermanagement.service.CustomerService;
import com.readingisgood.bookordermanagement.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @Operation(summary = "Create a customer")
    @PostMapping(value = "/createCustomer",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
        CustomerDTO customerDTO =  customerService.createCustomer(createCustomerRequest);
        if(customerDTO == null){
            return new ResponseEntity<CustomerDTO>(HttpStatus.FOUND);
        }
        return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get  customer Orders By Customer Id")
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@PathVariable Long id,
                                                            @PageableDefault(size = 20) Pageable pageable) {
        try {
            List<OrderDTO> orderDTOs = orderService.getCustomerDoneOrders(id, pageable);

            if (orderDTOs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Get a customer By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {

        Customer customer = customerService.findCustomerById(id);
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CustomerDTO customerDTO =  CustomerDTO.fromCustomer(customer);
        return new ResponseEntity<>(customerDTO,HttpStatus.OK);

    }

}

