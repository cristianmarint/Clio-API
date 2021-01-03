/*
 * @Author: cristianmarint
 * @Date: 8/12/20 22:05
 */

package com.api.clio.model;

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
import java.util.ArrayList;
import java.util.Collections;
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
    public void addToCategoryList(Category category) {
        if (categoryList == null){
            categoryList = new ArrayList<>(Collections.singleton(category));
        }else {
            categoryList.add(category);
        }
    }

    public void removeFromCategoryList(Category category) {
        if(categoryList!=null) categoryList.remove(category);
    }


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(targetEntity = Author.class)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authorList;
    public void addToAuthorList(Author author){
        if (authorList == null){
            authorList = new ArrayList<>(Collections.singleton(author));
        }else {
            authorList.add(author);
        }
    }

    public void removeFromAuthorList(Author author) {
        if (author != null) authorList.remove(author);
    }
}
