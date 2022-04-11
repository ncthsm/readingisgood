package com.readingisgood.bookordermanagement.service;

import com.readingisgood.bookordermanagement.dto.CustomerMonthlyStatisticsDTO;

import java.util.List;

public interface StatisticsService {

    public List<CustomerMonthlyStatisticsDTO> getCustomerMonthlyStatics(Long customerId);

}
