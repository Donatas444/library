package com.example.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String author;
    private String category;
    private String language;
    private LocalDate datePublished;
    private String isbn;
    private String uuid;
    private boolean availability;
    private LocalDate dateTook;
    private LocalDate dateReturn;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
}
