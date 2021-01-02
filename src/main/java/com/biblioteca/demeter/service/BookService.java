/*
 * @Author: cristianmarint
 * @Date: 2/1/21 8:24
 */

package com.biblioteca.demeter.service;

import com.biblioteca.demeter.dto.BookDto;
import com.biblioteca.demeter.exceptions.BadRequestException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.mapper.BookMapper;
import com.biblioteca.demeter.mapper.CategoryMapper;
import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.model.Book;
import com.biblioteca.demeter.model.Category;
import com.biblioteca.demeter.repository.AuthorRepository;
import com.biblioteca.demeter.repository.BookRepository;
import com.biblioteca.demeter.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private CategoryMapper categoryMapper;

//    /api/books/{id}
    /**
     * @return BookDto
     * @throws ResourceNotFoundException
     */
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() throws ResourceNotFoundException{
        List<BookDto> bookDtoList = bookRepository.findAll()
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        if (bookDtoList == null) throw new ResourceNotFoundException();
        return bookDtoList;
    }

    /**
     * @param bookId
     * @return BookDto
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    @Transactional(readOnly = true)
    public BookDto getBook(Long bookId) throws BadRequestException, ResourceNotFoundException {
        if (bookId == null) throw new BadRequestException("BookID cannot be Null or Empty");
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResourceNotFoundException(bookId,"Book"));
        return bookMapper.mapBookToDto(book);
    }

    /**
     * @param bookDto
     * @return
     * @throws BadRequestException
     */
    public BookDto createBook(BookDto bookDto) throws BadRequestException {
        validateBookDto(bookDto);
        Book save=bookRepository.save(bookMapper.mapDtoToBook(bookDto));
        bookDto.setId(save.getId());
        return bookDto;
    }

    /**
     * @param bookId
     * @param bookDto
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    @Transactional
    public void updateBook(Long bookId, BookDto bookDto) throws BadRequestException, ResourceNotFoundException {
        validateBookDto(bookDto);
        if (bookId==null) throw new BadRequestException("BookId cannot be Null or Empty");
        if (bookRepository.findById(bookId).isPresent()){
            bookDto.setId(bookId);
            bookRepository.save(bookMapper.mapDtoToBook(bookDto));
        }else {
            throw new ResourceNotFoundException(bookId,"Book");
        }
    }

    /**
     * @param bookId
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    public void deleteBook(Long bookId) throws ResourceNotFoundException, BadRequestException {
        if (bookId==null) throw new BadRequestException("BookId cannot be Null or Empty");
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new ResourceNotFoundException(bookId, "Book"));
        if (book != null) {
            ListIterator<Author> authorListIterator = authorRepository.findAuthorsByBookId(bookId).listIterator();
            while (authorListIterator.hasNext()){
                Author authorToUpdate = authorListIterator.next();
                book.removeFromAuthorList(authorToUpdate);
                authorToUpdate.removeFromBookList(book);
            }
            ListIterator<Category> categoryListIterator = categoryRepository.findCategoryByBookId(bookId).listIterator();
            while (categoryListIterator.hasNext()){
                Category categoryToUpdate = categoryListIterator.next();
                book.removeFromCategoryList(categoryToUpdate);
                categoryToUpdate.removeFromBookList(book);
            }
            bookRepository.deleteById(bookId);
        }
    }

    /**
     * @param bookDto
     * @throws BadRequestException
     */
    public void validateBookDto(BookDto bookDto) throws BadRequestException{
        if (bookDto == null) throw new BadRequestException("Book cannot be null");
        if (bookDto.getName() == null)throw new BadRequestException("Book name cannot be null");
    }
}