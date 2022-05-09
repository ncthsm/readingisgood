package com.readingisgood.bookordermanagement.dto;

import com.readingisgood.bookordermanagement.model.BookStock;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookDTO {

    private String bookName;

    private String author;

    private Date publishDate;

    private String publisher;

    private Integer stock;

    private Double amount;

    private Date createDate;

    private Long bookId;

    public static BookDTO fromBook(BookStock bookStock){
        return  BookDTO.builder().bookId(bookStock.getId()).bookName(bookStock.getBookName()).
                amount(bookStock.getAmount()).createDate(bookStock.getCreatedDate()).
                author(bookStock.getAuthor()).publishDate(bookStock.getPublishDate()).
                publisher(bookStock.getPublisher()).stock(bookStock.getStock()).build();

    }



}
