/*
 * @Author: cristianmarint
 * @Date: 11/12/20 9:45
 */

package com.biblioteca.demeter.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank(message = "Category name cannot be Empty or Null")
    private String name;

    @Nullable
    private String description;

    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());

    @ManyToMany(mappedBy = "categoryList",targetEntity = Book.class, fetch=FetchType.EAGER)
    private List<Book> bookList;
    public void addBookToList(Book book){
        if (bookList == null){
            bookList = new ArrayList<Book>(Collections.singleton(book));
        }else {
            bookList.add(book);
        }
    }
}
