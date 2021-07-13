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

        double l1 = Math.random() * (10);
        double l2 = Math.random() * (10);
        double b1 = Math.random() * (10);
        double b2 = Math.random() * (10);
        double b3 = Math.random() * (10);
        double v1 = Math.random() * (10);
        double v2 = Math.random() * (10);

        if ((int) l1 == 0 && (int) l2 == 0) {
            l2++;
        }
        if ((int) b1 == 0) {
            b1++;
        }
        if ((int) v1 == 0 && (int) v2 == 0) {
            v2++;
        }
        double c = (hashOp((int) l1) + l2 + hashOp((int) b1) + b2 + hashOp((int) b3) + v1 + hashOp((int) v2)) % 10;
        countryCode = (int) l1 + "" + (int) l2;
        titleIdentifier = (int) b1 + "" + (int) b2 + "" + (int) b3;
        publisherNumber = (int) v1 + "" + (int) v2;
        checkNr = (int) c + "";
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
            throw new NoSuchElementException("Book not found" + id);
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
