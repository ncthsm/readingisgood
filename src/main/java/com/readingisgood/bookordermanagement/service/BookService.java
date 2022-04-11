package com.readingisgood.bookordermanagement.service;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.Book;

public interface BookService {

    public BookDTO addBook(AddBookRequest addBookRequest);
    public boolean deleteBook(Long bookId);
    public BookDTO updateBookStock(UpdateBookStockRequest updateBookRequest);
    public BookDTO updateBookAmount(UpdateBookAmountRequest updateBookPriceRequest);
    public boolean getBooks();
    public Book findBookById(Long bookId);
    public Book getBookFromStock(Long bookId, int stock);

}
