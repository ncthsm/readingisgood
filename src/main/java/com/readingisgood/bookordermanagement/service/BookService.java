package com.readingisgood.bookordermanagement.service;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.BookStock;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    public BookDTO addBook(AddBookRequest addBookRequest);
    public boolean deleteBook(Long bookId);
    public BookDTO updateBookStock(UpdateBookStockRequest updateBookRequest);
    public BookDTO updateBookAmount(UpdateBookAmountRequest updateBookPriceRequest);
    public List<BookDTO> getBooks(Pageable pageable);
    public BookStock findBookById(Long bookId);
    public BookStock getBookFromStock(Long bookId, int stock);
    public void saveBooks(List<BookStock> bookStockList);

}
