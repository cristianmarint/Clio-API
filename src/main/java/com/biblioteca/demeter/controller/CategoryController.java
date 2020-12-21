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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) throws ResourceNotFoundException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(id));
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "id") Long id,@RequestBody CategoryDto categoryDto){
        try{
            categoryService.updateCategory(id,categoryDto);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException | BadRequestException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @Min(1) Long id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.accepted().build();
        }  catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
