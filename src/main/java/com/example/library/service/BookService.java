package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Client;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientService clientService;

    public void addBook(Book book) throws NullPointerException {
        Book newBook = new Book();
        newBook.setName(book.getName());
        newBook.setAuthor(book.getAuthor());
        newBook.setCategory(book.getCategory());
        newBook.setLanguage(book.getLanguage());
        newBook.setDatePublished(book.getDatePublished());
        newBook.setIsbn(generateISBN());
        newBook.setUuid(generateUUID());
        newBook.setAvailability(true);
        bookRepository.save(newBook);
    }

    public String generateISBN() {
        String countryCode;
        String titleIdentifier;
        String publisherNumber;
        String checkNr;

        double L1 = Math.random() * (10);
        double L2 = Math.random() * (10);
        double B1 = Math.random() * (10);
        double B2 = Math.random() * (10);
        double B3 = Math.random() * (10);
        double V1 = Math.random() * (10);
        double V2 = Math.random() * (10);

        if ((int) L1 == 0 && (int) L2 == 0) {
            L2++;
        }
        if ((int) B1 == 0) {
            B1++;
        }
        if ((int) V1 == 0 && (int) V2 == 0) {
            V2++;
        }
        double C = (hashOp((int) L1) + L2 + hashOp((int) B1) + B2 + hashOp((int) B3) + V1 + hashOp((int) V2)) % 10;
        countryCode = (int) L1 + "" + (int) L2;
        titleIdentifier = (int) B1 + "" + (int) B2 + "" + (int) B3;
        publisherNumber = (int) V1 + "" + (int) V2;
        checkNr = (int) C + "";
        return "ISBN " + countryCode + "-" + titleIdentifier + "-" + publisherNumber + "-" + checkNr;
    }

    public int hashOp(int i) {
        int doubled = 2 * i;
        if (doubled >= 10) {
            doubled = doubled - 9;
        }
        return doubled;
    }

    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void deleteBook(Long id) throws NoSuchElementException {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooks() throws NoSuchElementException {
        return bookRepository.findAll();
    }

    public List<Book> filterBooksByName() {
        return bookRepository.filterBooksByName();
    }

    public List<Book> filterBooksByAuthor() {
        return bookRepository.filterBooksByAuthor();
    }

    public List<Book> filterBooksByIsbn() {
        return bookRepository.filterBooksByIsbn();
    }

    public List<Book> filterBooksByLanguage() {
        return bookRepository.filterBooksByLanguage();
    }

    public List<Book> filterBooksByCategory() {
        return bookRepository.filterBooksByCategory();
    }

    public List<Book> isAvailable(boolean canTake) throws IllegalArgumentException {
        return bookRepository.filterBooksByAvailability(canTake);
    }

    public Book getBookById(Long id) throws NoSuchElementException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new RuntimeException("Book not found" + id);
        }
    }

    public void takeBook(Long id, int period) throws NoSuchElementException {
        Book book = getBookById(id);
        LocalDate dateBookTook = LocalDate.now();
        LocalDate dateOfReturning = dateBookTook.plusDays(period);
        if (period >= 62) {
            book.setDateReturn(dateBookTook.plusMonths(2));
        } else book.setDateReturn(dateOfReturning);
        book.setDateTook(dateBookTook);
        book.setAvailability(false);
        bookRepository.save(book);
    }

    public void clientBooksCount(Long id, Long clientId, int period) throws NoSuchElementException {
        Book book = getBookById(id);
        Client client = clientService.getClientById(clientId);
        List<Book> books = client.getBooks();
        if (books.size() < 3) {
            books.add(book);
            book.setClient(client);
            takeBook(id, period);
        } else throw new ArrayStoreException("Only 3 books available to have at a time");
    }

    public void returnBook(Long id) throws NoSuchElementException {
        Book book = getBookById(id);
        Client client = book.getClient();
        book.setAvailability(true);
        book.setClient(null);
        book.setDateTook(null);
        book.setDateReturn(null);
        client.getBooks().remove(book);
        bookRepository.save(book);
    }

    public Book getBookByUiid(String uuid) {
        return bookRepository.findByUiid(uuid);
    }
}
