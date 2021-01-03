/*
 * @Author: cristianmarint
 * @Date: 17/12/20 10:57
 */

package com.api.clio.mapper;

import com.api.clio.dto.AuthorDto;
import com.api.clio.model.Author;
import com.api.clio.model.Book;
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
