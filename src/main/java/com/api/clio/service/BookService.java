/*
 * @Author: cristianmarint
 * @Date: 2/1/21 8:24
 */

package com.api.clio.service;

import com.api.clio.dto.BookDto;
import com.api.clio.dto.CategoryDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.mapper.BookMapper;
import com.api.clio.mapper.CategoryMapper;
import com.api.clio.model.Author;
import com.api.clio.model.Book;
import com.api.clio.model.Category;
import com.api.clio.repository.AuthorRepository;
import com.api.clio.repository.BookRepository;
import com.api.clio.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
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

    /**
     * @param bookId
     * @return
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    public List<CategoryDto> getAllBookCategories(Long bookId) throws BadRequestException, ResourceNotFoundException {
        if (bookId==null) throw new BadRequestException("BookId cannot be Null or Empty");

        List<CategoryDto> categoryDtoList = categoryRepository.findCategoryByBookId(bookId)
                    .stream()
                    .map(categoryMapper::mapCategoryToDto)
                    .collect(Collectors.toList());
        if (categoryDtoList == null || categoryDtoList.isEmpty()) throw new ResourceNotFoundException();
        return categoryDtoList;
    }

    /**
     * @param bookId
     * @param categoryId
     * @return
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    public CategoryDto getBookCategory(Long bookId,Long categoryId) throws BadRequestException, ResourceNotFoundException {
        if (bookId == null || categoryId == null) throw new BadRequestException();
        Category category = categoryRepository.findCategoryByBookIdAndCategoryId(bookId,categoryId).orElseThrow(()-> new ResourceNotFoundException(categoryId,"Category"));
        return categoryMapper.mapCategoryToDto(category);
    }

    /**
     * @param bookId
     * @param categoryId
     * @return
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    public List<CategoryDto> createBookCategoryRelation(Long bookId, Long categoryId) throws BadRequestException, ResourceNotFoundException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");

        log.info("Service createBookCategoryRelation");

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        Optional<Book> relationExist = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId);
        if(!relationExist.isPresent()){
            log.info("relationExist.isPresent()");
            category.addToBookList(book);
            book.addToCategoryList(category);
            bookRepository.save(book);
        }

        return this.getAllBookCategories(categoryId);
    }

    /**
     * @param bookId
     * @param categoryId
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    public void deleteBookCategoryRelation(Long bookId, Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        if(bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId).isPresent()){
            category.removeFromBookList(book);
            book.removeFromCategoryList(category);
            bookRepository.deleteCategoryBookRelationByCategoryIdAndBookId(categoryId,bookId);
        }else{
            throw new ResourceNotFoundException();
        }
    }
}