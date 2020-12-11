/*
 * @Author: cristianmarint
 * @Date: 11/12/20 9:45
 */

package com.biblioteca.demeter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

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

    private String description;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "categoryList")
    private List<Book> bookList;
}
