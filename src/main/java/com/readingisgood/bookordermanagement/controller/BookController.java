package com.readingisgood.bookordermanagement.controller;

import com.readingisgood.bookordermanagement.controller.request.AddBookRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookAmountRequest;
import com.readingisgood.bookordermanagement.controller.request.UpdateBookStockRequest;
import com.readingisgood.bookordermanagement.dto.BookDTO;
import com.readingisgood.bookordermanagement.model.Book;
import com.readingisgood.bookordermanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Add Book")
    @PostMapping(value = "/addBook")
    private BookDTO addBook(@RequestBody AddBookRequest addBookRequest){
        return bookService.addBook(addBookRequest);
    }

    @Operation(summary = "Delete book from stock")
    @DeleteMapping(value = "/deleteBook/{bookId}")
    private boolean deleteBook(@PathVariable String bookId){
        return bookService.deleteBook(Long.valueOf(bookId));
    }

    @Operation(summary = "Update book Price from stock")
    @PutMapping(value = "/updateBookPrice")
    private BookDTO updateBookPrice(@RequestBody UpdateBookAmountRequest updateBookAmountRequest){
        return bookService.updateBookAmount(updateBookAmountRequest);
    }

    @Operation(summary = "Update book Stock")
    @PutMapping(value = "/updateBookStock")
    private BookDTO updateBookStock(@RequestBody UpdateBookStockRequest updateBookRequest){
        return bookService.updateBookStock(updateBookRequest);
    }

    @Operation(summary = "List book from stock with paging")
    @PostMapping(value = "/listBooks")
    private ResponseEntity<List<BookDTO>> listBooks(@RequestBody Pageable pageable){
        List<BookDTO> bookDTOList = bookService.getBooks(pageable);
        if(bookDTOList == null || bookDTOList.isEmpty()){
           return new ResponseEntity<List<BookDTO>>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<List<BookDTO>>(bookDTOList,HttpStatus.OK);
        }
    }


}
