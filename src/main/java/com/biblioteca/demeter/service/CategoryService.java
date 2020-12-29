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

//    https://www.apascualco.com/spring-boot/spring-transactional/
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategory(Long categoryId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(categoryId,"Category"));
        return categoryMapper.mapCategoryToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) throws BadRequestException {
        validateCategory(categoryDto);
        Category save=categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryDto categoryDto) throws ResourceNotFoundException, BadRequestException {
        validateCategory(categoryDto);
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

    public void validateCategory(CategoryDto categoryDto) throws BadRequestException{
        if (categoryDto == null) {
            throw new BadRequestException("Category cannot be null");
        }else if (categoryDto.getName() == null){
            throw new BadRequestException("Category name cannot be null");
        }
    }


//    ManyToMany

    @Transactional(readOnly = true)
    public List<BookDto> getAllCategoryBooks(Long categoryId) throws BadRequestException {
        if (categoryId == null) throw new BadRequestException("CategoryId cannot be Null or Empty");
        return bookRepository.findBooksByCategoryId(categoryId)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public BookDto getCategoryBook(Long categoryId, Long bookId) throws ResourceNotFoundException, BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId and BookId cannot be Null or Empty");
        Book book = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId).orElseThrow(()-> new ResourceNotFoundException(bookId,"Book"));
        return bookMapper.mapBookToDto(book);
    }

    public Object createCategoryBookRelation(Long categoryId, Long bookId) throws ResourceNotFoundException,BadRequestException {
        if (categoryId == null || bookId == null) throw new BadRequestException("CategoryId and BookId cannot be Null or Empty");

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(()->new ResourceNotFoundException(categoryId,"Category"));
        Book book = bookRepository.findBookById(bookId).orElseThrow(()->new ResourceNotFoundException(bookId,"Book"));

        Optional<Book> relationExist = bookRepository.findBookByCategoryIdAndBookId(categoryId,bookId);
        if(relationExist.isEmpty()){
            category.addBookToList(book);
            book.addCategoryToList(category);
            categoryRepository.save(category);
        }
        return this.getAllCategoryBooks(categoryId);
    }
}
