/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:07
 */

package com.biblioteca.demeter.boot;

import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Order(3)
@Slf4j
public class AuthorDataLoader implements CommandLineRunner {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("---------- 3 - LOADING AUTHORS ----------");
        authorRepository.deleteAllInBatch();
        Author author1 = Author.builder()
                .name("Jostein Gaarder")
                .dateOfBirth(Instant.parse("1952-08-12T08:25:24.00Z"))
                .build();
        authorRepository.save(author1);

        Author author2 = Author.builder()
                .name("Charles Bukowski")
                .dateOfBirth(Instant.parse("1920-08-16T08:25:24.00Z"))
                .dateOfDeath(Instant.parse("1994-03-08T08:25:24.00Z"))
                .build();
        authorRepository.save(author2);

        Author author3 = Author.builder()
                .name("Test Author")
                .dateOfBirth(Instant.parse("1920-08-16T08:25:24.00Z"))
                .dateOfDeath(Instant.parse("1994-03-08T08:25:24.00Z"))
                .build();
        authorRepository.save(author3);
    }
}
