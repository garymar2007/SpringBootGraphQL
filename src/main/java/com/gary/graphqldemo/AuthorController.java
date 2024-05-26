package com.gary.graphqldemo;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @QueryMapping
    Iterable<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    Optional<Author> authorById(@Argument("id") Long id) {
        return authorRepository.findById(id);
    }

    @MutationMapping
    Book addBook(@Argument("book") BookInput book) {
        Author author = authorRepository.findById(book.authorId()).orElseThrow(() ->
                new IllegalArgumentException("Author not found"));
        Book newBook = new Book(book.title(), book.publisher(), author);
        return bookRepository.save(newBook);
    }

    record BookInput(String title, String publisher, Long authorId) {
    }
}
