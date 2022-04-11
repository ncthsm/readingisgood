package com.readingisgood.bookordermanagement.controller;

import com.readingisgood.bookordermanagement.controller.request.AddItemToBasketRequest;
import com.readingisgood.bookordermanagement.controller.request.ListOrdersByDateRequest;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import com.readingisgood.bookordermanagement.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/listOrdersByDate")
    private boolean listOrdersByDate(@RequestBody ListOrdersByDateRequest ordersByDateRequest) {
        return true;
    }

    @Operation(summary = "Get Order")
    @PostMapping("/getOrder")
    public boolean getBasketListByCustomerId(Long customerId) {
        orderService.getOrderByStatusAndCustomerId(OrderStatus.BASKET, customerId);
        return true;
    }

    @Operation(summary = "Add Item Basket")
    @PostMapping("/addItemToBasket")
    public ResponseEntity<OrderDTO> addItemToBasket(@RequestBody AddItemToBasketRequest addItemToBasketRequest) {

        try {
            OrderDTO orderDTO = orderService.addItemToBasket(addItemToBasketRequest);
            if (orderDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order fetching ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Confirm Basket")
    @PostMapping("/confirmBasket/{customerId}")
    public ResponseEntity<OrderDTO> confirmBasket(Long customerId){

        try {
            OrderDTO orderDTO = orderService.confirmBasket(customerId);
            if (orderDTO == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order confirming ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Delete Basket")
    @DeleteMapping("/deleteBasket/{customerId}")
    public ResponseEntity<Boolean> deleteBasket(@PathVariable Long customerId){

        try{
            boolean retVal = orderService.deleteBasket(customerId);

            if(retVal == true){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Operation(summary = "Get Order")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {

        try {
            OrderDTO orderDto = orderService.getOrderById(id);
            if (orderDto == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orderDto, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order fetching ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Orders By Date Interval")
    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getOrdersByDateInterval(@DateTimeFormat(iso = DATE) @RequestParam LocalDate startDate,
                                                                  @DateTimeFormat(iso = DATE) @RequestParam LocalDate endDate,
                                                                  @PageableDefault(size = 20)Pageable pageable) {

        try {
            List<OrderDTO> orders = orderService.getOrdersByDate(startDate.atStartOfDay(), endDate.atStartOfDay(),pageable);
            if (orders == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during order fetching ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
