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
import java.sql.Timestamp;
import java.time.Instant;
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
    private Instant dateOfBirth;

    @Nullable
    @Builder.Default
    private boolean alive = true;

    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());

    @ManyToMany()
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> bookList;
}
