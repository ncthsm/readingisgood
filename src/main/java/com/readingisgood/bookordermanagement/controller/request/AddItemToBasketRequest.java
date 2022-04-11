package com.readingisgood.bookordermanagement.controller.request;

import com.readingisgood.bookordermanagement.model.OrderItem;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class AddItemToBasketRequest {

    @NotNull
    Long customerId;

    OrderItem orderItem;

}
