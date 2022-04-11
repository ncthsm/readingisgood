package com.readingisgood.bookordermanagement.controller;

import com.readingisgood.bookordermanagement.dto.CustomerMonthlyStatisticsDTO;
import com.readingisgood.bookordermanagement.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerMonthlyStatisticsDTO>> getCustomerMonthlyStatics(@PathVariable String customerId) {

        log.info("Customer monthly statics request is received with customerId={}", customerId);

        try {
            List<CustomerMonthlyStatisticsDTO> customerMonthlyStatics = statisticsService.getCustomerMonthlyStatics(Long.valueOf(customerId));
            return new ResponseEntity<>(customerMonthlyStatics, HttpStatus.OK);

        } catch (Exception ex) {
            log.error("Exception is occurred during customer monthly statics querying ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
