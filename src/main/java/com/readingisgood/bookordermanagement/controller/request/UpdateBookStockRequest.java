package com.readingisgood.bookordermanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookStockRequest {
    
    Long bookId;
    Integer stock;
    
}
