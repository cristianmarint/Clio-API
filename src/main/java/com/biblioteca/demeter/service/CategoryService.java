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
    public CategoryDto getCategory(Long id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id,"Category"));
        return categoryMapper.mapCategoryToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) throws BadRequestException {
        validateCategory(categoryDto);
        Category save=categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }

    @Transactional
    public void updateCategory(Long id, CategoryDto categoryDto) throws ResourceNotFoundException, BadRequestException {
        validateCategory(categoryDto);
        if(categoryRepository.findById(id).isPresent()){
            categoryDto.setId(id);
            categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        }else{
            throw new ResourceNotFoundException(id,"Category");
        }
    }

    public void deleteCategory(Long id) throws ResourceNotFoundException {
        if(categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException(id,"Category");
        }
    }

    public void validateCategory(CategoryDto categoryDto) throws BadRequestException{
        if (categoryDto == null) {
            throw new BadRequestException("Category cannot be null");
        }else if (categoryDto.getName() == null){
            throw new BadRequestException("Category name cannot be null");
        }
    }


    @Transactional(readOnly = true)
    public List<BookDto> getAllCategoryBooks(Long categoryId) {
        return bookRepository.findBooksByCategoryId(categoryId)
                .stream()
                .map(bookMapper::mapBookToDto)
                .collect(toList());
    }
}
