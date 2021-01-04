/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:14
 */

package com.api.clio.service;

import com.api.clio.dto.AuthorDto;
import com.api.clio.dto.BookDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.mapper.AuthorMapper;
import com.api.clio.mapper.BookMapper;
import com.api.clio.model.Author;
import com.api.clio.model.Book;
import com.api.clio.repository.AuthorRepository;
import com.api.clio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Validated
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private BookMapper bookMapper;


    /**
     * Find all authors on database
     * @return List<AuthorDto> List of authors
     * @throws ResourceNotFoundException when authors were not found
     */
//    /authors/{authorId}
    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() throws ResourceNotFoundException {
        List<AuthorDto> authorDtoList = authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorToDto)
                .collect(toList());
        if(authorDtoList==null) throw new ResourceNotFoundException();
        return authorDtoList;
    }

    /**
     * find author base on Id
     * @param authorId Id of a registered author
     * @return AuthorDto Author data response
     * @throws ResourceNotFoundException when author is not found
     * @throws BadRequestException when authorId is missing necessary values
     */
    @Transactional(readOnly = true)
    public AuthorDto getAuthor(@Min(1) Long authorId) throws ResourceNotFoundException, BadRequestException {
        if (authorId == null) throw new BadRequestException("AuthorId cannot be Null or Empty");
        Author author = authorRepository.findById(authorId)
                .orElseThrow(()-> new ResourceNotFoundException(authorId,"Author"));
        return authorMapper.mapAuthorToDto(author);
    }

    /**
     * creates a new author on database
     * @param authorDto Author data
     * @return AuthorDto Author data
     * @throws BadRequestException when AuthorDto is missing necessary values
     */
    public AuthorDto createAuthor(@Valid AuthorDto authorDto) throws BadRequestException {
        validateAuthor(authorDto);
        Author author = authorRepository.save(authorMapper.mapDtoToAuthor(authorDto));
        authorDto.setId(author.getId());
        return authorDto;
    }

    /**
     * Updates an author on database
     * @param authorId Author Id
     * @param authorDto Author data
     * @throws BadRequestException when AuthorDto or AuthorId is messing necessary values
     * @throws ResourceNotFoundException when Author is not found
     */
    @Transactional
    public void updateAuthor(@Min(1) Long authorId, @Valid AuthorDto authorDto) throws BadRequestException,ResourceNotFoundException{
        validateAuthor(authorDto);
        if (authorId == null) throw new BadRequestException();
        if (authorRepository.findById(authorId).isPresent()){
            authorDto.setId(authorId);
            authorRepository.save(authorMapper.mapDtoToAuthor(authorDto));
        }else{
            throw new ResourceNotFoundException(authorId,"Author");
        }
    }

    /**
     * Deletes an author from database
     * @param authorId Author Id
     * @throws ResourceNotFoundException when Id is not found
     */
    public void deleteAuthor(@Min(1) Long authorId) throws ResourceNotFoundException, BadRequestException {
        if (authorId == null) throw new BadRequestException("AuthorId cannot be Null or Empty");
        Author author = authorRepository.findById(authorId).orElseThrow(()->new ResourceNotFoundException(authorId,"Author"));

        if(author!=null){
            ListIterator<Book> bookListIterator = bookRepository.findBooksByAuthorId(authorId).listIterator();
            while(bookListIterator.hasNext()){
                Book bookToUpdate = bookListIterator.next();
                author.removeFromBookList(bookToUpdate);
                bookToUpdate.removeFromAuthorList(author);
            }
            authorRepository.deleteById(authorId);
        }
    }

    /**
     * Check if required values are set
     * @param authorDto Author data
     * @throws BadRequestException when AuthorDto is messing required values
     */
    public void validateAuthor(AuthorDto authorDto) throws BadRequestException{
        if (authorDto == null) {
            throw new BadRequestException("Author cannot be null");
        }else if (authorDto.getName() == null){
            throw new BadRequestException("Author name cannot be null");
        }
    }

//    /api/authors/{authorId}/books/{bookId}

    /**
     * Find all books related to an author on database
     * @param authorId Author's Id
     * @return BookDto Book Data
     * @throws BadRequestException when authorId is missing value
     * @throws ResourceNotFoundException when no books were found
     */
    public List<BookDto> getAllAuthorBooks(Long authorId) throws BadRequestException, ResourceNotFoundException {
        if (authorId == null) throw new BadRequestException("AuthorId Cannot be Null or Empty");

        List<BookDto> bookDtoList = bookRepository.findBooksByAuthorId(authorId)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(toList());
        if (bookDtoList == null || bookDtoList.isEmpty()) throw new ResourceNotFoundException();
        return bookDtoList;
    }

    /**
     * @param authorId Author's Id
     * @param bookId Book's Id
     * @return BookDto Book Data
     * @throws BadRequestException when authorId or bookId are missing values
     * @throws ResourceNotFoundException when Book is not found
     */
    public BookDto getAuthorBook(Long authorId, Long bookId) throws BadRequestException, ResourceNotFoundException {
        if (bookId == null || authorId == null) throw new BadRequestException();
        Book book = bookRepository.findBookByAuthorIdAndBookId(authorId,bookId).orElseThrow(()-> new ResourceNotFoundException(bookId,"Book"));
        return bookMapper.mapBookToDto(book);
    }

    /**
     * Links a given author to a given book via Id's
     * @param bookId Id of the book
     * @param authorId Id of the books author
     * @return List<BookDto> List of books data for the author
     * @throws ResourceNotFoundException when author or book is not found
     * @throws BadRequestException when authorId or bookId are invalid
     */
    public List<BookDto> createAuthorBookRelation(Long authorId, Long bookId) throws BadRequestException, ResourceNotFoundException {
        if (authorId == null || bookId == null) throw new BadRequestException("AuthorId or BookId cannot be Null or Empty");

        Author author = authorRepository.findById(authorId).orElseThrow(()->new ResourceNotFoundException(authorId,"Author"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        Optional<Author> relationExist = authorRepository.findAuthorByBookIdAndAuthorId(authorId,bookId);
        if(!relationExist.isPresent()){
            author.addToBookList(book);
            book.addToAuthorList(author);
            authorRepository.save(author);
        }

        return this.getAllAuthorBooks(bookId);
    }

    /**
     * Remove link a given author to a given book via Id's
     * @param bookId Id of the book
     * @param authorId Id of the books author
     * @throws ResourceNotFoundException when author or book is not found
     * @throws BadRequestException when authorId or bookId are invalid
     */
    public void deleteAuthorBookRelation(Long authorId, Long bookId) throws ResourceNotFoundException, BadRequestException {
        if (authorId == null || bookId == null) throw new BadRequestException("AuthorId or BookId cannot be Null or Empty");

        Author author = authorRepository.findById(authorId).orElseThrow(()-> new ResourceNotFoundException(authorId,"Author"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        if(bookRepository.findBookByAuthorIdAndBookId(authorId,bookId).isPresent()){
            author.removeFromBookList(book);
            book.removeFromAuthorList(author);
            bookRepository.deleteCategoryBookRelationByCategoryIdAndBookId(authorId,bookId);
        }else{
            throw new ResourceNotFoundException();
        }
    }

}
