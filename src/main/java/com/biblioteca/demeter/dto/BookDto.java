/*
 * @Author: cristianmarint
 * @Date: 25/12/20 11:49
 */

package com.biblioteca.demeter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String isbn;
    private String image;
    private String publicationDate;
    private boolean shared;

//    TODO: Return category info (if comments remove n:n query)
//    private List<Category> categoryList;
//    private List<Author> authorList;

    private Integer numberOfCategories;
    private Integer numberOfAuthors;
}
