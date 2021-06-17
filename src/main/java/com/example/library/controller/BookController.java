package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/books/name")
    public List<Book> filterBooksByName() {
        return bookService.filterBooksByName();
    }

    @GetMapping("/books/author")
    public List<Book> filterBooksByAuthor() {
        return bookService.filterBooksByAuthor();
    }

    @GetMapping("/books/category")
    public List<Book> filterBooksByCategory() {
        return bookService.filterBooksByCategory();
    }

    @GetMapping("/books/language")
    public List<Book> filterBooksByLanguage() {
        return bookService.filterBooksByLanguage();
    }

    @GetMapping("/books/isbn")
    public List<Book> filterBooksByIsbn() {
        return bookService.filterBooksByIsbn();
    }

    @GetMapping("/books/{availability}")
    public List<Book> filterBooksByAvailability(@PathVariable("availability") boolean canTake) {
        return bookService.isAvailable(canTake);
    }

    @PutMapping("book/{id}/{clientId}/{period}")
    public void takeBook(@PathVariable("id") Long id, @PathVariable("clientId") Long clientId, @PathVariable("period") int days) {
        bookService.clientBooksCount(id, clientId, days);
    }

    @PutMapping("book/{id}")
    public void returnBook(@PathVariable("id") Long id) {
        bookService.returnBook(id);
    }

    @GetMapping("/book/{uuid}")
    public Book getBookByUiid(@PathVariable("uuid") String uuid) {
        return bookService.getBookByUiid(uuid);
    }
}
