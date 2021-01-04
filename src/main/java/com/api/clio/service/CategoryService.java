/*
 * @Author: cristianmarint
 * @Date: 12/12/20 17:00
 */

package com.api.clio.service;

import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.model.Book;
import com.api.clio.model.Category;
import com.api.clio.repository.BookRepository;
import com.api.clio.repository.CategoryRepository;
import com.api.clio.dto.BookDto;
import com.api.clio.dto.CategoryDto;
import com.api.clio.mapper.BookMapper;
import com.api.clio.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BookMapper bookMapper;

//    /api/categories/{id}

    /**
     * Find all categories on database
     * @return List<CategoryDto> List of categories
     * @throws ResourceNotFoundException when categories were not found
     */
//    https://www.apascualco.com/spring-boot/spring-transactional/
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() throws ResourceNotFoundException {
        List<CategoryDto> categoryDtoList = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToDto)
                .collect(toList());
        if(categoryDtoList==null) throw new ResourceNotFoundException();
        return categoryDtoList;
    }

    /**
     * @param categoryId Id of a registered category
     * @return CategoryDto Category data response
     * @throws ResourceNotFoundException when category is not found
     * @throws BadRequestException when categoryId is missing necessary values
     */
    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(categoryId,"Category"));
        return categoryMapper.mapCategoryToDto(category);
    }

    /**
     * creates a new category on database
     * @param categoryDto Category Data
     * @return CategoryDto Category Data
     * @throws BadRequestException when CategoryDto is missing necessary values.
     */
    public CategoryDto createCategory(CategoryDto categoryDto) throws BadRequestException {
        validateCategoryDto(categoryDto);
        Category save=categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    /**
     * Updates a category on database
     * @param categoryId Category Id
     * @param categoryDto Category Data
     * @throws ResourceNotFoundException when Category is not found
     * @throws BadRequestException when CategoryId is missing necessary values.
     */
    @Transactional
    public void updateCategory(Long categoryId, CategoryDto categoryDto) throws ResourceNotFoundException, BadRequestException {
        validateCategoryDto(categoryDto);
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        if(categoryRepository.findById(categoryId).isPresent()){
            categoryDto.setId(categoryId);
            categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        }else{
            throw new ResourceNotFoundException(categoryId,"Category");
        }
    }

    /**
     * @param categoryId
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    public void deleteCategory(Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));

        if(category!=null){
            ListIterator<Book> bookListIterator = bookRepository.findBooksByCategoryId(categoryId).listIterator();
            while(bookListIterator.hasNext()){
                Book bookToUpdate = bookListIterator.next();
                category.removeFromBookList(bookToUpdate);
                bookToUpdate.removeFromCategoryList(category);
            }
            categoryRepository.deleteById(categoryId);
        }
    }

    /**
     * @param categoryDto
     * @throws BadRequestException
     */
    public void validateCategoryDto(CategoryDto categoryDto) throws BadRequestException{
        if (categoryDto == null) {
            throw new BadRequestException("Category cannot be null");
        }else if (categoryDto.getName() == null){
            throw new BadRequestException("Category name cannot be null");
        }
    }


//    /api/categories/{id}/books/{id}

    /**
     * @param categoryId
     * @return
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    @Transactional(readOnly = true)
    public List<BookDto> getAllCategoryBooks(Long categoryId) throws BadRequestException,ResourceNotFoundException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");

        List<BookDto> bookDtoList = bookRepository.findBooksByCategoryId(categoryId)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(toList());
        if (bookDtoList == null || bookDtoList.isEmpty()) throw new ResourceNotFoundException();
        return bookDtoList;
    }

    /**
     * @param categoryId
     * @param bookId
     * @return
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    @Transactional(readOnly = true)
    public BookDto getCategoryBook(Long categoryId, Long bookId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");
        Book book = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId).orElseThrow(()-> new ResourceNotFoundException(bookId,"Book"));
        return bookMapper.mapBookToDto(book);
    }

    /**
     * @param categoryId
     * @param bookId
     * @return
     * @throws ResourceNotFoundException
     * @throws BadRequestException
     */
    public List<BookDto> createCategoryBookRelation(Long categoryId, Long bookId) throws ResourceNotFoundException,BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        Optional<Book> relationExist = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId);
        if(!relationExist.isPresent()){
            category.addToBookList(book);
            book.addToCategoryList(category);
            categoryRepository.save(category);
        }
        return this.getAllCategoryBooks(categoryId);
    }

    /**
     * @param categoryId
     * @param bookId
     * @throws BadRequestException
     * @throws ResourceNotFoundException
     */
    public void deleteCategoryBookRelation(Long categoryId, Long bookId) throws BadRequestException, ResourceNotFoundException {
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
