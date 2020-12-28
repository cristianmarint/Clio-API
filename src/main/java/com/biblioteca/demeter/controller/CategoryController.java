/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:55
 */

package com.biblioteca.demeter.controller;

import com.biblioteca.demeter.dto.CategoryDto;
import com.biblioteca.demeter.exceptions.BadRequestException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name="categoryId") Long categoryId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "categoryId") Long categoryId,@RequestBody CategoryDto categoryDto){
        try{
            categoryService.updateCategory(categoryId,categoryDto);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException | BadRequestException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "categoryId") @Min(1) Long categoryId) {
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.accepted().build();
        }  catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{categoryId}/books")
    public ResponseEntity<?> getAllCategoryBooks(@PathVariable(name = "categoryId") Long categoryId){
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategoryBooks(categoryId));
    }
}
