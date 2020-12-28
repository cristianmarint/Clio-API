/*
 * @Author: cristianmarint
 * @Date: 12/12/20 17:04
 */

package com.biblioteca.demeter.mapper;

import com.biblioteca.demeter.dto.CategoryDto;
import com.biblioteca.demeter.model.Book;
import com.biblioteca.demeter.model.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "numberOfBooks",expression = "java(mapBooks(category.getBookList()))")
    CategoryDto mapCategoryToDto(Category category);

    default Integer mapBooks(List<Book> numberOfBooks){
        return numberOfBooks.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "bookList", ignore = true)
    Category mapDtoToCategory(CategoryDto categoryDto);
}
