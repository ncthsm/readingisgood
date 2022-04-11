package com.readingisgood.bookordermanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;

    private String gsm;

    @NotNull
    private String address;

}
