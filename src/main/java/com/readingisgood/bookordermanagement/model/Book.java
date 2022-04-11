package com.readingisgood.bookordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "book_sequence";

    private String bookName;

    private String author;

    private String publisher;

    private Date publishDate;

    private Double amount;

    private Integer stock;

    @Builder.Default
    private Date createdDate = Date.from(Instant.now());

}
