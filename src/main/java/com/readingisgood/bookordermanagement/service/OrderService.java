package com.readingisgood.bookordermanagement.service;

import com.readingisgood.bookordermanagement.controller.request.AddItemToBasketRequest;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.model.Order;
import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDTO getOrderByStatusAndCustomerId(OrderStatus orderStatus, Long customerId);
    OrderDTO addItemToBasket(AddItemToBasketRequest addItemToBasketRequest);
    Page<Order> getOrderDoneStatusAndCustomerId(Long customerId, Pageable page);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getOrdersByDate(LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);
    List<OrderDTO> getOrdersByCustomerId(String customerId, Pageable pageable);
    List<OrderDTO> getCustomerDoneOrders(Long customerId, Pageable pageable);
    OrderDTO confirmBasket(Long customerId);
    boolean deleteBasket(Long customerId);
}
