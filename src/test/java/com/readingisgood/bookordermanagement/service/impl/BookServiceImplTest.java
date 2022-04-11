package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.Book;
import com.readingisgood.bookordermanagement.repository.BookRepository;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    void addBook() {

        String bookName = "Book";
        String author = "Author";
        String publisher = "Publisher";
        Date publishDate = Date.from(Instant.now());
        Integer stock = 50;
        Double amount = 19.99;

        AddBookRequest request = new AddBookRequest(bookName,author,publishDate, publisher, stock, amount);

        Book book = Book.builder()
                .bookName(bookName).author(author)
                .publishDate(publishDate).stock(stock)
                .amount(amount).publisher(publisher)
                .build();

        when(bookRepository.findByAuthorAndPublisherAndBookName(author,publisher,bookName))
                .thenReturn(Optional.empty());

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDTO bookDto = bookService.addBook(request);

        assertNotNull(bookDto);
        assertEquals(bookDto.getBookName(), book.getBookName());
        assertEquals(bookDto.getAmount(), book.getAmount());


    }

    @Test
    void addBookToExistingStock() {

        Long bookId = 100L;
        Integer existingStock = 1;
        String bookName = "Book";
        String author = "Author";
        String publisher = "Publisher";
        Date publishDate = Date.from(Instant.now());
        Integer newStock = 50;
        Double amount = 19.99;

        Book book = Book.builder().stock(1).build();
        Book updatedBook = Book.builder().stock(newStock+existingStock).build();

        when(bookRepository.findByAuthorAndPublisherAndBookName(author,publisher,bookName)).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        AddBookRequest request = new AddBookRequest(bookName,author,publishDate, publisher, newStock, amount);

        BookDTO bookDto = bookService.addBook(request);

        assertNotNull(bookDto);
        assertEquals(newStock+existingStock, bookDto.getStock());


    }

    @Test
    void deleteBook(){

        Long bookId = 1L;

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Book()));

        Boolean status = bookService.deleteBook(bookId);

        assertNotNull(status);
        assertEquals(true,status);

    }

    @Test
    void getBookFromStock_success(){

        Long bookId = 5L;
        Integer stock = 100;

        Book book = Book.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(book));

        Book dbBook = bookService.getBookFromStock(5L,10);

        assertNotNull(dbBook);
        assertEquals(dbBook.getId(),bookId);
    }

    @Test
    void getBookFromStock_fail(){

        Long bookId = 5L;
        Integer stock = 100;

        Book book = Book.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(book));

        Book dbBook = bookService.getBookFromStock(5L,101);

        assertNull(dbBook);
    }

    @Test
    void getBookFromStock_emptyBook(){

        Long bookId = 5L;
        Integer stock = 100;

        Book book = Book.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        Book dbBook = bookService.getBookFromStock(5L,101);

        assertNull(dbBook);
    }

    @Test
    void updateBookStock() {

        Long bookId = 100L;
        Integer newStock = 20;

        Book book = Book.builder().stock(1).build();
        Book updatedBook = Book.builder().stock(newStock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest(bookId,newStock);

        BookDTO bookDto = bookService.updateBookStock(updateBookStockRequest);

        assertNotNull(bookDto);
        assertEquals(newStock, bookDto.getStock());
    }

    @Test
    void updateBookAmount() {

        Double newAmount = 10.0;

        Book book = Book.builder().stock(1).build();
        Book updatedBook = Book.builder().amount(newAmount).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(updatedBook);

        UpdateBookAmountRequest updateBookAmountRequest = new UpdateBookAmountRequest(1L,10.0);

        BookDTO bookDto = bookService.updateBookAmount(updateBookAmountRequest);

        assertNotNull(bookDto);
        assertEquals(updatedBook.getAmount(), bookDto.getAmount());
    }

}