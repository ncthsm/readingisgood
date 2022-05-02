package com.readingisgood.bookordermanagement.service.impl;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.Book;
import com.readingisgood.bookordermanagement.repository.BookRepository;
import com.readingisgood.bookordermanagement.service.BookService;
import com.readingisgood.bookordermanagement.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public BookDTO addBook(AddBookRequest addBookRequest) {

        Optional<Book>  bookOptional = bookRepository.findByAuthorAndPublisherAndBookName(addBookRequest.getAuthor(),
                addBookRequest.getPublisher(),addBookRequest.getBookName());

        log.info("Receive addBook request ={} ",addBookRequest.toString());

        if(bookOptional.isPresent()){
            bookOptional.get().setStock(bookOptional.get().getStock() + addBookRequest.getStock());
            bookRepository.save(bookOptional.get());
            log.info("Update Book ={}",bookOptional.get().toString());
            return BookDTO.fromBook(bookOptional.get());
        }else{
            Book newBook = Book.builder().bookName(addBookRequest.getBookName()).publisher(addBookRequest.getPublisher()).
                    author(addBookRequest.getAuthor()).publishDate(addBookRequest.getPublishDate()).
                    amount(addBookRequest.getAmount()).id(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME)).
                    stock(addBookRequest.getStock()).build();
            log.info("Created New Book ={}",newBook.toString());
            return BookDTO.fromBook(newBook);
        }

    }

    @Override
    public boolean deleteBook(Long bookId) {

        Optional<Book> book = bookRepository.findById(bookId);

        if(book.isEmpty()){
            log.info("Delete Error, book not found by id= {}",bookId);
            return false;
        }

        bookRepository.delete(book.get());
        return true;
    }

    @Override
    public BookDTO updateBookStock(UpdateBookStockRequest updateBookRequest) {

        Optional<Book> bookOptional = bookRepository.findById(updateBookRequest.getBookId());

        log.info("Receive update book stock request={}",updateBookRequest.toString());

        if(bookOptional.isEmpty()){
            log.info("Error,Book not found, bookId ={}",updateBookRequest.getBookId());
            return null;
        }

        if(updateBookRequest.getStock() <= 0){
            log.info("Receive update book stock request={}",updateBookRequest.toString());
            return null;
        }

        log.info("Updated book stock ,bookId",updateBookRequest.getBookId());
        bookOptional.get().setStock(updateBookRequest.getStock());
        bookRepository.save(bookOptional.get());

        return BookDTO.fromBook(bookOptional.get());
    }

    @Override
    public BookDTO updateBookAmount(UpdateBookAmountRequest updateBookAmountRequest) {

        Optional<Book> bookOptional = bookRepository.findById(updateBookAmountRequest.getBookId());

        log.info("Receive update book amount request={}",updateBookAmountRequest.toString());

        if(bookOptional.isEmpty()){
            log.info("Error,Book not found, bookId ={}",updateBookAmountRequest.getBookId());
            return null;
        }

        log.info("Updated book stock ,bookId",updateBookAmountRequest.getBookId());
        bookOptional.get().setAmount(updateBookAmountRequest.getAmount());
        bookRepository.save(bookOptional.get());

        return BookDTO.fromBook(bookOptional.get());
    }

    @Override
    public List<BookDTO> getBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.toList().stream().map(book-> BookDTO.fromBook(book)).collect(Collectors.toList());
    }

    @Override
    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    public Book getBookFromStock(Long bookId, int stock){

        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            log.error("Book is not found ,bookId={}", bookId);
            return null;
        } else if (bookOpt.get().getStock() < stock) {
            log.error("Book does have enough stock , bookId={} existingStock={} requestedStock={}",
                    bookId, bookOpt.get().getStock(), stock);
            return null;
        }
        return bookOpt.get();

    }

    @Override
    public void saveBooks(List<Book> bookList) {
        for (Book book : bookList) {
            bookRepository.save(book);
        }
    }


}
