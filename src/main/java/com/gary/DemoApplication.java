package com.gary;

import com.gary.graphqldemo.Author;
import com.gary.graphqldemo.AuthorRepository;
import com.gary.graphqldemo.Book;
import com.gary.graphqldemo.BookRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Using graphiql: http://localhost:8080/graphiql for any queries.
 */

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            Author gary = authorRepository.save(new Author(null, "Gary"));
            Author carol = authorRepository.save(new Author(null, "Carol"));
            bookRepository.saveAll(List.of(
                new Book("SpringBoot", "IT Publish", gary),
                new Book("GraphQL", "Microsoft Press", gary),
                new Book("Java", "Tsinghua University Press", carol)
            ));
        };
    }
}