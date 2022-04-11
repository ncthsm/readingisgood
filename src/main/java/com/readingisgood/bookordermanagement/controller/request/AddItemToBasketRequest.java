package com.readingisgood.bookordermanagement.controller.request;

import com.readingisgood.bookordermanagement.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AddItemToBasketRequest {

    @NotNull
    Long customerId;

    OrderItem orderItem;

}
