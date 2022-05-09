package com.readingisgood.bookordermanagement.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {

    @NotNull
    private String bookName;

    @NotNull
    private String author;

    private Date publishDate;

    @NotNull
    private String publisher;

    @NotNull
    private Integer stock;

    private Double amount;

}
