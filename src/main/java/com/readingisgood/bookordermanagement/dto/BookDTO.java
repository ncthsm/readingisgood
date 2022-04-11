package com.readingisgood.bookordermanagement.dto;

import com.readingisgood.bookordermanagement.model.Book;
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

    public static BookDTO fromBook(Book book){
        return  BookDTO.builder().bookId(book.getId()).bookName(book.getBookName()).
                amount(book.getAmount()).createDate(book.getCreatedDate()).
                author(book.getAuthor()).publishDate(book.getPublishDate()).
                publisher(book.getPublisher()).stock(book.getStock()).build();

    }



}
