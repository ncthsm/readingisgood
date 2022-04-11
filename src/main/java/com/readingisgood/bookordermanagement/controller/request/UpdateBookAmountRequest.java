package com.readingisgood.bookordermanagement.controller.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class UpdateBookAmountRequest {

    @NotNull
    Long bookId;

    @NotNull
    Double amount;

}
