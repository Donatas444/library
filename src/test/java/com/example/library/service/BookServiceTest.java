package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.library.model.Book;
import com.example.library.model.Client;
import com.example.library.repository.BookRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BookService.class, ClientService.class})
@ExtendWith(SpringExtension.class)
public class BookServiceTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @MockBean
    private ClientService clientService;

    @Test
    public void testAddBook() throws NullPointerException, NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book);
        this.bookService.addBook(new Book());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testAddBook2() throws NullPointerException {
        when(this.bookRepository.save(any())).thenThrow(new ArrayStoreException("foo"));
        assertThrows(ArrayStoreException.class, () -> this.bookService.addBook(new Book()));
        verify(this.bookRepository).save(any());
    }

    @Test
    public void testAddBook3() throws NullPointerException {
        when(this.bookRepository.save(any())).thenThrow(new NullPointerException("foo"));
        Book book = mock(Book.class);
        when(book.getDatePublished()).thenThrow(new RuntimeException("An error occurred"));
        when(book.getLanguage()).thenReturn("foo");
        when(book.getCategory()).thenReturn("foo");
        when(book.getAuthor()).thenReturn("foo");
        when(book.getName()).thenReturn("foo");
        assertThrows(RuntimeException.class, () -> this.bookService.addBook(book));
        verify(book).getAuthor();
        verify(book).getCategory();
        verify(book).getDatePublished();
        verify(book).getLanguage();
        verify(book).getName();
    }

    @Test
    public void testGetBookById() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        assertSame(book, this.bookService.getBookById(123L));
        verify(this.bookRepository).findById(any());
    }

    @Test
    public void testGetBookById2() throws NoSuchElementException {
        when(this.bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.bookService.getBookById(123L));
        verify(this.bookRepository).findById(any());
    }

    @Test
    public void testTakeBook() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setLanguage("Language");
        book1.setIsbn("Isbn");
        book1.setClient(client1);
        book1.setDatePublished(LocalDate.ofEpochDay(1L));
        book1.setUuid("1234");
        book1.setId(123L);
        book1.setDateReturn(LocalDate.ofEpochDay(1L));
        book1.setName("Name");
        book1.setCategory("Category");
        book1.setAuthor("JaneDoe");
        book1.setDateTook(LocalDate.ofEpochDay(1L));
        book1.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book1);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        this.bookService.takeBook(123L, 1);
        verify(this.bookRepository).findById(any());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testTakeBook2() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book);
        when(this.bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.bookService.takeBook(123L, 1));
        verify(this.bookRepository).findById(any());
    }

    @Test
    public void testTakeBook3() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setLanguage("Language");
        book1.setIsbn("Isbn");
        book1.setClient(client1);
        book1.setDatePublished(LocalDate.ofEpochDay(1L));
        book1.setUuid("1234");
        book1.setId(123L);
        book1.setDateReturn(LocalDate.ofEpochDay(1L));
        book1.setName("Name");
        book1.setCategory("Category");
        book1.setAuthor("JaneDoe");
        book1.setDateTook(LocalDate.ofEpochDay(1L));
        book1.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book1);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        this.bookService.takeBook(123L, 62);
        verify(this.bookRepository).findById(any());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testClientBooksCount() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        when(this.clientService.getClientById(any())).thenReturn(client);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client1);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);

        Client client2 = new Client();
        client2.setId(123L);
        client2.setName("Name");
        client2.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setLanguage("Language");
        book1.setIsbn("Isbn");
        book1.setClient(client2);
        book1.setDatePublished(LocalDate.ofEpochDay(1L));
        book1.setUuid("1234");
        book1.setId(123L);
        book1.setDateReturn(LocalDate.ofEpochDay(1L));
        book1.setName("Name");
        book1.setCategory("Category");
        book1.setAuthor("JaneDoe");
        book1.setDateTook(LocalDate.ofEpochDay(1L));
        book1.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book1);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        this.bookService.clientBooksCount(123L, 123L, 3);
        verify(this.clientService).getClientById(any());
        verify(this.bookRepository, times(2)).findById(any());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testClientBooksCount2() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        when(this.clientService.getClientById(any())).thenReturn(client);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client1);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book);
        when(this.bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.bookService.clientBooksCount(123L, 123L, 3));
        verify(this.bookRepository).findById(any());
    }

    @Test
    public void testClientBooksCount3() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        when(this.clientService.getClientById(any())).thenReturn(client);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client1);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);

        Client client2 = new Client();
        client2.setId(123L);
        client2.setName("Name");
        client2.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setLanguage("Language");
        book1.setIsbn("Isbn");
        book1.setClient(client2);
        book1.setDatePublished(LocalDate.ofEpochDay(1L));
        book1.setUuid("1234");
        book1.setId(123L);
        book1.setDateReturn(LocalDate.ofEpochDay(1L));
        book1.setName("Name");
        book1.setCategory("Category");
        book1.setAuthor("JaneDoe");
        book1.setDateTook(LocalDate.ofEpochDay(1L));
        book1.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book1);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        this.bookService.clientBooksCount(123L, 123L, 62);
        verify(this.clientService).getClientById(any());
        verify(this.bookRepository, times(2)).findById(any());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testReturnBook() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        Optional<Book> ofResult = Optional.of(book);

        Client client1 = new Client();
        client1.setId(123L);
        client1.setName("Name");
        client1.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setLanguage("Language");
        book1.setIsbn("Isbn");
        book1.setClient(client1);
        book1.setDatePublished(LocalDate.ofEpochDay(1L));
        book1.setUuid("1234");
        book1.setId(123L);
        book1.setDateReturn(LocalDate.ofEpochDay(1L));
        book1.setName("Name");
        book1.setCategory("Category");
        book1.setAuthor("JaneDoe");
        book1.setDateTook(LocalDate.ofEpochDay(1L));
        book1.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book1);
        when(this.bookRepository.findById(any())).thenReturn(ofResult);
        this.bookService.returnBook(123L);
        verify(this.bookRepository).findById(any());
        verify(this.bookRepository).save(any());
        assertTrue(this.bookService.getBooks().isEmpty());
    }

    @Test
    public void testReturnBook2() throws NoSuchElementException {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setLanguage("Language");
        book.setIsbn("Isbn");
        book.setClient(client);
        book.setDatePublished(LocalDate.ofEpochDay(1L));
        book.setUuid("1234");
        book.setId(123L);
        book.setDateReturn(LocalDate.ofEpochDay(1L));
        book.setName("Name");
        book.setCategory("Category");
        book.setAuthor("JaneDoe");
        book.setDateTook(LocalDate.ofEpochDay(1L));
        book.setAvailability(true);
        when(this.bookRepository.save(any())).thenReturn(book);
        when(this.bookRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.bookService.returnBook(123L));
        verify(this.bookRepository).findById(any());
    }
}

