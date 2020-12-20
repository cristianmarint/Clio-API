/*
 * @Author: cristianmarint
 * @Date: 11/12/20 9:45
 */

package com.biblioteca.demeter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.Instant;
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

    @NotBlank(message = "Category name cannot be Empty or Null")
    private String name;

    @Nullable
    private String description;

    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "categoryList")
    private List<Book> bookList;
}
