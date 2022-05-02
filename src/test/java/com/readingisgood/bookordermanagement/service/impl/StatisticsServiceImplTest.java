package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.dto.CustomerMonthlyStatisticsDTO;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.model.OrderItem;
import com.readingisgood.bookordermanagement.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StatisticsServiceImplTest {

    @InjectMocks
    private StatisticsServiceImpl staticsService;

    @Mock
    private OrderService orderService;

    @Test
    void getCustomerMonthlyStatics() {

        Long customerId = 101L;
        OrderItem bookOrder1 = new OrderItem(1L, 1);
        OrderItem bookOrder2 = new OrderItem(2L, 2);
        OrderItem bookOrder3 = new OrderItem(3L, 3);
        OrderItem bookOrder4 = new OrderItem(4L, 4);

        OrderDTO orderDto1 = OrderDTO.builder()
                .doneDate(LocalDateTime.of(2022, 10, 10, 12, 30))
                .orderItems(Arrays.asList(bookOrder1, bookOrder2))
                .totalAmount(30.0)
                .totalQuantity(3)
                .build();

        OrderDTO orderDto2 = OrderDTO.builder()
                .doneDate(LocalDateTime.of(2022, 10, 9, 12, 30))
                .orderItems(Arrays.asList(bookOrder3, bookOrder4))
                .totalAmount(40.0)
                .totalQuantity(7)
                .build();

        List<OrderDTO> orderDTOS;

        when(orderService.getCustomerDoneOrders(customerId,Pageable.unpaged())).thenReturn(Arrays.asList(orderDto1, orderDto2));
        List<CustomerMonthlyStatisticsDTO> customerMonthlyStatics = staticsService.getCustomerMonthlyStatics(customerId);

        assertNotNull(customerMonthlyStatics);
        assertEquals(1, customerMonthlyStatics.size());
        assertEquals("OCTOBER", customerMonthlyStatics.get(0).getMonth());
        assertEquals(2, customerMonthlyStatics.get(0).getTotalOrderCount());
        assertEquals(10, customerMonthlyStatics.get(0).getTotalBookCount());
        assertEquals(70, customerMonthlyStatics.get(0).getTotalPurchasedAmount());
    }

}