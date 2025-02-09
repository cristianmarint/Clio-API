/*
 * @Author: cristianmarint
 * @Date: 11/12/20 9:45
 */

package com.api.clio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @ManyToMany(mappedBy = "categoryList",targetEntity = Book.class, fetch=FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Book> bookList;
    public void addToBookList(Book book){
        if (bookList == null){
            bookList = new ArrayList<>(Collections.singleton(book));
        }else {
            bookList.add(book);
        }
    }

    public void removeFromBookList(Book book) {
        if(bookList!=null){
            bookList.remove(book);
        }
    }
}
