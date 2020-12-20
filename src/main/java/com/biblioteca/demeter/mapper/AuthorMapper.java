/*
 * @Author: cristianmarint
 * @Date: 17/12/20 10:57
 */

package com.biblioteca.demeter.mapper;

import com.biblioteca.demeter.dto.AuthorResponse;
import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.model.Book;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "dateOfBirth", expression = "java(mapDateOfBirthToDto(author))")
    @Mapping(target = "numberOfBooks",expression = "java(mapBooks(author.getBookList()))")
    AuthorResponse mapAuthorToDto(Author author);
    default String mapDateOfBirthToDto(Author author){
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(Locale.forLanguageTag("en")).build();
        return TimeAgo.using(author.getDateOfBirth().toEpochMilli(),messages);
    }
    default Integer mapBooks(List<Book> numberOfBooks){
        return numberOfBooks.size();
    }



    @InheritInverseConfiguration
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "bookList", ignore = true)
    Author mapDtoToAuthor(AuthorResponse authorResponse);
}
