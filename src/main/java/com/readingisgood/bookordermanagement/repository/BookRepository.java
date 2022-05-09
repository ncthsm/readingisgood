package com.readingisgood.bookordermanagement.repository;

import com.readingisgood.bookordermanagement.model.BookStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<BookStock,Long> {

    Page<BookStock> findAll(Pageable pageable);
    Optional<BookStock> findByAuthorAndPublisherAndBookName(String author, String publisher, String bookName);
    Optional<BookStock> findByBookName(String bookName);

}
