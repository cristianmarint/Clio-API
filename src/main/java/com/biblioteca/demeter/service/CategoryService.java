/*
 * @Author: cristianmarint
 * @Date: 12/12/20 17:00
 */

package com.biblioteca.demeter.service;

import com.biblioteca.demeter.dto.BookDto;
import com.biblioteca.demeter.dto.CategoryDto;
import com.biblioteca.demeter.exceptions.BadRequestException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.mapper.BookMapper;
import com.biblioteca.demeter.mapper.CategoryMapper;
import com.biblioteca.demeter.model.Book;
import com.biblioteca.demeter.model.Category;
import com.biblioteca.demeter.repository.BookRepository;
import com.biblioteca.demeter.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(categoryId,"Category"));
        return categoryMapper.mapCategoryToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) throws BadRequestException {
        validateCategoryDto(categoryDto);
        Category save=categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

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

    public void deleteCategory(Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        if(categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
        }else {
            throw new ResourceNotFoundException(categoryId,"Category");
        }
    }

    public void validateCategoryDto(CategoryDto categoryDto) throws BadRequestException{
        if (categoryDto == null) {
            throw new BadRequestException("Category cannot be null");
        }else if (categoryDto.getName() == null){
            throw new BadRequestException("Category name cannot be null");
        }
    }


//    /api/categories/{id}/books/{id}

    @Transactional(readOnly = true)
    public List<BookDto> getAllCategoryBooks(Long categoryId) throws BadRequestException,ResourceNotFoundException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");

        List<BookDto> bookDtoList = bookRepository.findBooksByCategoryId(categoryId)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(toList());
        if (bookDtoList == null) throw new ResourceNotFoundException();
        return bookDtoList;
    }

    @Transactional(readOnly = true)
    public BookDto getCategoryBook(Long categoryId, Long bookId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");
        Book book = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId).orElseThrow(()-> new ResourceNotFoundException(bookId,"Book"));
        return bookMapper.mapBookToDto(book);
    }

    public List<BookDto> createCategoryBookRelation(Long categoryId, Long bookId) throws ResourceNotFoundException,BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId or BookId cannot be Null or Empty");

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        Optional<Book> relationExist = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId);
        if(relationExist.isEmpty()){
            category.addToBookList(book);
            book.addToCategoryList(category);
            categoryRepository.save(category);
        }
        return this.getAllCategoryBooks(categoryId);
    }

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
