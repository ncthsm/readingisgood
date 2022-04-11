package com.readingisgood.bookordermanagement.model;

import com.readingisgood.bookordermanagement.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    private Long customerId;

    private OrderStatus orderStatus;

    private LocalDateTime doneDate;

    private List<OrderItem> orderItems;

    private Double totalAmount;

    private Integer totalQuantity;

    @Builder.Default
    private Date createdDate = Date.from(Instant.now());

}
