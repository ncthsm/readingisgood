package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.BookStock;
import com.readingisgood.bookordermanagement.repository.BookRepository;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookStockServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;


    @Test
    void addBook() {

        String bookName = "BookStock";
        String author = "Author";
        String publisher = "Publisher";
        Date publishDate = Date.from(Instant.now());
        Integer stock = 50;
        Double amount = 19.99;

        AddBookRequest request = new AddBookRequest(bookName,author,publishDate, publisher, stock, amount);

        BookStock bookStock = BookStock.builder()
                .bookName(bookName).author(author)
                .publishDate(publishDate).stock(stock)
                .amount(amount).publisher(publisher)
                .build();

        when(bookRepository.findByAuthorAndPublisherAndBookName(author,publisher,bookName))
                .thenReturn(Optional.empty());

        when(bookRepository.save(Mockito.any(BookStock.class))).thenReturn(bookStock);
        BookDTO bookDto = bookService.addBook(request);

        assertNotNull(bookDto);
        assertEquals(bookDto.getBookName(), bookStock.getBookName());
        assertEquals(bookDto.getAmount(), bookStock.getAmount());


    }

    @Test
    void addBookToExistingStock() {

        Long bookId = 100L;
        Integer existingStock = 1;
        String bookName = "BookStock";
        String author = "Author";
        String publisher = "Publisher";
        Date publishDate = Date.from(Instant.now());
        Integer newStock = 50;
        Double amount = 19.99;

        BookStock bookStock = BookStock.builder().stock(1).build();
        BookStock updatedBookStock = BookStock.builder().stock(newStock+existingStock).build();

        when(bookRepository.findByAuthorAndPublisherAndBookName(author,publisher,bookName)).thenReturn(Optional.of(bookStock));
        when(bookRepository.save(Mockito.any(BookStock.class))).thenReturn(updatedBookStock);

        AddBookRequest request = new AddBookRequest(bookName,author,publishDate, publisher, newStock, amount);

        BookDTO bookDto = bookService.addBook(request);

        assertNotNull(bookDto);
        assertEquals(newStock+existingStock, bookDto.getStock());


    }

    @Test
    void deleteBook(){

        Long bookId = 1L;

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new BookStock()));

        Boolean status = bookService.deleteBook(bookId);

        assertNotNull(status);
        assertEquals(true,status);

    }

    @Test
    void getBookFromStock_success(){

        Long bookId = 5L;
        Integer stock = 100;

        BookStock bookStock = BookStock.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(bookStock));

        BookStock dbBookStock = bookService.getBookFromStock(5L,10);

        assertNotNull(dbBookStock);
        assertEquals(dbBookStock.getId(),bookId);
    }

    @Test
    void getBookFromStock_fail(){

        Long bookId = 5L;
        Integer stock = 100;

        BookStock bookStock = BookStock.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(bookStock));

        BookStock dbBookStock = bookService.getBookFromStock(5L,101);

        assertNull(dbBookStock);
    }

    @Test
    void getBookFromStock_emptyBook(){

        Long bookId = 5L;
        Integer stock = 100;

        BookStock bookStock = BookStock.builder().id(bookId).stock(stock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());

        BookStock dbBookStock = bookService.getBookFromStock(5L,101);

        assertNull(dbBookStock);
    }

    @Test
    void updateBookStock() {

        Long bookId = 100L;
        Integer newStock = 20;

        BookStock bookStock = BookStock.builder().stock(1).build();
        BookStock updatedBookStock = BookStock.builder().stock(newStock).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(bookStock));
        when(bookRepository.save(Mockito.any(BookStock.class))).thenReturn(updatedBookStock);

        UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest(bookId,newStock);

        BookDTO bookDto = bookService.updateBookStock(updateBookStockRequest);

        assertNotNull(bookDto);
        assertEquals(newStock, bookDto.getStock());
    }

    @Test
    void updateBookAmount() {

        Double newAmount = 10.0;

        BookStock bookStock = BookStock.builder().stock(1).build();
        BookStock updatedBookStock = BookStock.builder().amount(newAmount).build();

        when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(bookStock));
        when(bookRepository.save(Mockito.any(BookStock.class))).thenReturn(updatedBookStock);

        UpdateBookAmountRequest updateBookAmountRequest = new UpdateBookAmountRequest(1L,10.0);

        BookDTO bookDto = bookService.updateBookAmount(updateBookAmountRequest);

        assertNotNull(bookDto);
        assertEquals(updatedBookStock.getAmount(), bookDto.getAmount());
    }


    @Test
    void getBooks() {

        BookStock bookStock = BookStock.builder().bookName("necati").id(5L).stock(100).author("HAsim").build();
        List<BookStock> bookStockList = Arrays.asList(bookStock);
        Page<BookStock> books = new PageImpl<BookStock>(bookStockList);
        when(bookRepository.findAll(Pageable.ofSize(5))).thenReturn(books);
        List<BookDTO> bookPage = bookService.getBooks(Pageable.ofSize(5));
        assertNotNull(bookPage);
    }
}