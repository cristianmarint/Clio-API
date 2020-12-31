/*
 * @Author: cristianmarint
 * @Date: 23/12/20 17:20
 */

package com.biblioteca.demeter.boot;

import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.model.Book;
import com.biblioteca.demeter.model.Category;
import com.biblioteca.demeter.model.User;
import com.biblioteca.demeter.repository.AuthorRepository;
import com.biblioteca.demeter.repository.BookRepository;
import com.biblioteca.demeter.repository.CategoryRepository;
import com.biblioteca.demeter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@Order(4)
@Slf4j
public class BookDataLoader  implements CommandLineRunner {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("---------- 4 - LOADING BOOKS ----------");
        bookRepository.deleteAllInBatch();
        Category category1 = categoryRepository.findCategoryById(1L).orElseThrow(()->new ResourceNotFoundException());
        Category category2 = categoryRepository.findCategoryById(2L).orElseThrow(()->new ResourceNotFoundException());

        Author author1 = authorRepository.findAuthorById(1L).orElseThrow(()->new ResourceNotFoundException());

        Author author2 = authorRepository.findAuthorById(2L).orElseThrow(()->new ResourceNotFoundException());
        Author author3 = authorRepository.findAuthorById(3L).orElseThrow(()->new ResourceNotFoundException());

        Optional<User> owner = Optional.ofNullable(userRepository.findByUsername("cristianmarint").orElseThrow(() -> new ResourceNotFoundException()));

        Book book1 = Book
                .builder()
                .name("El mundo de Sofía")
                .isbn("9788498415384")
                .language("spanish")
                .image("https://imagessl4.casadellibro.com/a/l/t5/84/9788498415384.jpg")
                .publicationDate(Instant.parse("2012-09-27T09:25:24.00Z"))
                .user(owner.get())
        .build();
        book1.addToAuthorList(author1);
        book1.addToCategoryList(category1);
        bookRepository.save(book1);

        Book book2 = Book
                .builder()
                .name("Women: A Novel\n")
                .isbn("9780061863769")
                .language("English")
                .image("https://images-na.ssl-images-amazon.com/images/I/8196vY9StNL.jpg")
                .publicationDate(Instant.parse("2009-10-13T09:25:24.00Z"))
                .user(owner.get())
                .build();
        book2.addToAuthorList(author2);
        book2.addToCategoryList(category2);
        bookRepository.save(book2);
    }
}
