/*
 * @Author: cristianmarint
 * @Date: 12/12/20 17:00
 */

package com.biblioteca.demeter.service;

import com.biblioteca.demeter.dto.CategoryDto;
import com.biblioteca.demeter.exceptions.DemeterException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.mapper.CategoryMapper;
import com.biblioteca.demeter.model.Category;
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
    private CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToDto)
                .collect(toList());
    }

    public CategoryDto getCategory(Long id){
        Category category= categoryRepository.findById(id)
                .orElseThrow(()-> new DemeterException("Category with ID "+id+" was not found"));
        return categoryMapper.mapCategoryToDto(category);
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category save=categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        categoryDto.setId(save.getId());
        return categoryDto;
    }


    public void updateCategory(Long id, CategoryDto categoryDto) throws ResourceNotFoundException{
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryDto.setId(id);
            categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDto));
        }else{
            throw new ResourceNotFoundException("Category with ID "+id+" was not found");
        }
    }

    public void deleteCategory(Long id) throws ResourceNotFoundException {
        if(categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Category with ID "+id+" was not found");
        }
    }
}
