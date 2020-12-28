/*
 * @Author: cristianmarint
 * @Date: 8/12/20 22:05
 */

package com.biblioteca.demeter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank(message = "Book name cannot be Empty or Null")
    private String name;

    @Nullable
    private String isbn;

    @Nullable
    private String language;

    @Nullable
    private String image;

    @Nullable
    private Instant publicationDate;

    @Nullable
    @Builder.Default
    private Boolean shared=false;

    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Category.class)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categoryList;



    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Author.class)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authorList;
}
