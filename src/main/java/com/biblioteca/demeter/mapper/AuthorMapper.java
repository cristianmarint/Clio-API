/*
 * @Author: cristianmarint
 * @Date: 17/12/20 10:57
 */

package com.biblioteca.demeter.mapper;

import com.biblioteca.demeter.dto.AuthorDto;
import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.model.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "numberOfBooks",expression = "java(mapBooks(author.getBookList()))")
    AuthorDto mapAuthorToDto(Author author);

    default Integer mapBooks(List<Book> numberOfBooks){
        return numberOfBooks.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "bookList", ignore = true)
    Author mapDtoToAuthor(AuthorDto authorDto);
}
