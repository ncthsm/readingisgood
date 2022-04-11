package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.dto.CustomerMonthlyStatisticsDTO;
import com.readingisgood.bookordermanagement.dto.OrderDTO;
import com.readingisgood.bookordermanagement.service.OrderService;
import com.readingisgood.bookordermanagement.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderService orderService;

    public List<CustomerMonthlyStatisticsDTO> getCustomerMonthlyStatics(Long customerId) {

        List<OrderDTO> customerOrders = orderService.getCustomerDoneOrders(customerId,Pageable.unpaged());

        Map<Month, List<OrderDTO>> result = customerOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getDoneDate().getMonth()));

        return result.entrySet().stream()
                .map(entry -> createCustomerMonthlyStatics(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private CustomerMonthlyStatisticsDTO createCustomerMonthlyStatics(Month month, List<OrderDTO> orderList) {
        int totalBookCount = 0;
        double totalPurchasedAmount = 0;

        for (OrderDTO order : orderList) {

            totalBookCount += order.getTotalQuantity();
            totalPurchasedAmount += order.getTotalAmount();
        }

        return new CustomerMonthlyStatisticsDTO(month.toString(), orderList.size(), totalBookCount, totalPurchasedAmount);
    }

}
