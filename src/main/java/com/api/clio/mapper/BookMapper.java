/*
 * @Author: cristianmarint
 * @Date: 28/12/20 10:56
 */

package com.api.clio.mapper;

import com.api.clio.dto.BookDto;
import com.api.clio.model.Author;
import com.api.clio.model.Book;
import com.api.clio.model.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "numberOfCategories", expression = "java(mapCategoryList(book.getCategoryList()))")
    @Mapping(target = "numberOfAuthors", expression = "java(mapAuthorList(book.getAuthorList()))")
    BookDto mapBookToDto(Book book);

    default Integer mapCategoryList(List<Category> bookList){
        return bookList.size();
    }
    default Integer mapAuthorList(List<Author> authorList){
        return authorList.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "authorList", ignore = true)
//    @Mapping(target = "categoryList", ignore = true)
    Book mapDtoToBook(BookDto bookDto);
}
