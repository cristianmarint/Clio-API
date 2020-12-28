/*
 * @Author: cristianmarint
 * @Date: 23/12/20 17:20
 */

package com.biblioteca.demeter.boot;

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
        Optional<Category> category1 = categoryRepository.findById(1L);
        Optional<Author> author1 = authorRepository.findById(1L);
        Optional<User> owner = userRepository.findByUsername("cristianmarint");

        Book book = Book
                .builder()
                .name("El mundo de Sofía")
                .isbn("9788498415384")
                .language("spanish")
                .image("https://imagessl4.casadellibro.com/a/l/t5/84/9788498415384.jpg")
                .publicationDate(Instant.parse("2012-09-27T09:25:24.00Z"))
//                .categoryList(Arrays.asList(category1.get()))
//                .authorList(Arrays.asList(author1.get()))
                .user(owner.get())
                .build();
//        log.info(String.valueOf(book));
        bookRepository.save(book);

        Book book1 = Book
                .builder()
                .name("Women: A Novel\n")
                .isbn("9780061863769")
                .language("English")
                .image("https://images-na.ssl-images-amazon.com/images/I/8196vY9StNL.jpg")
                .publicationDate(Instant.parse("2009-10-13T09:25:24.00Z"))
//                .categoryList(Arrays.asList(category1.get()))
//                .authorList(Arrays.asList(author1.get()))
                .user(owner.get())
                .build();
//        log.info(String.valueOf(book1));
        bookRepository.save(book1);
    }
}
