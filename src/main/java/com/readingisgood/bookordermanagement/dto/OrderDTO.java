package com.readingisgood.bookordermanagement.dto;


import com.readingisgood.bookordermanagement.model.Order;
import com.readingisgood.bookordermanagement.model.OrderItem;
import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

;

@Data
@Builder
public class OrderDTO {

    private Long id;

    private Long customerId;

    private OrderStatus orderStatus;

    private LocalDateTime doneDate;

    private List<OrderItem> orderItems;

    private Double totalAmount;

    private Integer totalQuantity;

    public static OrderDTO fromOrder(Order order) {

        return OrderDTO.builder()
                .customerId(order.getCustomerId())
                .orderItems(order.getOrderItems())
                .totalQuantity(order.getTotalQuantity())
                .orderStatus(order.getOrderStatus())
                .doneDate(order.getDoneDate())
                .totalAmount(order.getTotalAmount())
                .build();
    }

}
