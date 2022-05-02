package com.readingisgood.bookordermanagement.repository;

import com.readingisgood.bookordermanagement.model.Order;
import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order,Long> {

    Page<Order> getOrderByOrderStatusAndCustomerId(OrderStatus orderStatus, Long customerId, Pageable pageable);
    Optional<Order> getOrderByOrderStatusAndCustomerId(OrderStatus orderStatus,Long CustomerId);
    Page<Order> findByDoneDateBetweenAndOrderStatus(LocalDateTime startDate, LocalDateTime endDate,Pageable pageable,OrderStatus orderStatus);

}
