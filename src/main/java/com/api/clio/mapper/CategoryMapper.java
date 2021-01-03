/*
 * @Author: cristianmarint
 * @Date: 12/12/20 17:04
 */

package com.api.clio.mapper;

import com.api.clio.dto.CategoryDto;
import com.api.clio.model.Book;
import com.api.clio.model.Category;
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
