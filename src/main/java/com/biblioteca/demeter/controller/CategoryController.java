/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:55
 */

package com.biblioteca.demeter.controller;

import com.biblioteca.demeter.dto.CategoryDto;
import com.biblioteca.demeter.exceptions.BadResourceException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.model.Category;
import com.biblioteca.demeter.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getCategory(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(categoryDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "id") Long id,@RequestBody CategoryDto categoryDto){
        try{
            categoryService.update(id,categoryDto);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException exception){
            logger.error(exception.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        }  catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
