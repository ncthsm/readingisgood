package com.readingisgood.bookordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderItem {

    private Long bookId;
    private Integer quantity;

}

