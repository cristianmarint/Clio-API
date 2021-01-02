/*
 * @Author: cristianmarint
 * @Date: 8/12/20 22:14
 */

package com.biblioteca.demeter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author Name cannot be Blank")
    @Valid
    private String name;

    @Nullable
    @Builder.Default
    private Instant dateOfBirth = null;

    @Nullable
    @Builder.Default
    private Instant dateOfDeath = null;

    @Builder.Default
    private Instant createdAt= Instant.now();

    @ManyToMany(mappedBy = "authorList",targetEntity = Book.class, fetch=FetchType.EAGER)
    private List<Book> bookList;
    public void addToBookList(Book book){
        if (bookList == null){
            bookList = new ArrayList<>(Collections.singleton(book));
        }else {
            bookList.add(book);
        }
    }

    public void removeFromBookList(Book book) {
        if (book!=null) bookList.remove(book);
    }
}
