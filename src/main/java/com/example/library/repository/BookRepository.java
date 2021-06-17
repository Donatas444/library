package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * FROM book ORDER BY name asc", nativeQuery = true)
    List<Book> filterBooksByName();

    @Query(value = "SELECT * FROM book ORDER BY language asc", nativeQuery = true)
    List<Book> filterBooksByLanguage();

    @Query(value = "SELECT * FROM book ORDER BY category asc", nativeQuery = true)
    List<Book> filterBooksByCategory();

    @Query(value = "SELECT * FROM book ORDER BY isbn asc", nativeQuery = true)
    List<Book> filterBooksByIsbn();

    @Query(value = "SELECT * FROM book ORDER BY author asc", nativeQuery = true)
    List<Book> filterBooksByAuthor();

    @Query(value = "SELECT * FROM book WHERE availability = ?", nativeQuery = true)
    List<Book> filterBooksByAvailability(boolean canTake);

    @Query(value = "SELECT * FROM book WHERE uuid = ?", nativeQuery = true)
    Book findByUiid(String uuid);
}
