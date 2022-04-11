package com.readingisgood.bookordermanagement.repository;

import com.readingisgood.bookordermanagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,Long> {

    Page<Book> findAll(Pageable pageable);
    Optional<Book> findByAuthorAndPublisherAndBookName(String author,String publisher,String bookName);

}
