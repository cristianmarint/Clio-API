/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:55
 */

package com.api.clio.controller;

import com.api.clio.dto.BookDto;
import com.api.clio.dto.CategoryDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(categoryService.getAllCategories());
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(name="categoryId") Long categoryId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(categoryService.getCategory(categoryId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try{
            categoryService.createCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "categoryId") Long categoryId,@RequestBody CategoryDto categoryDto){
        try{
            categoryService.updateCategory(categoryId,categoryDto);
            return ResponseEntity.ok().build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "categoryId") Long categoryId) {
        try{
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{categoryId}/books")
    public ResponseEntity<List<BookDto>> getAllCategoryBooks(@PathVariable(name = "categoryId") Long categoryId){
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(categoryService.getAllCategoryBooks(categoryId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{categoryId}/books/{bookId}")
    public ResponseEntity<BookDto> getCategoryBook(@PathVariable(name="categoryId") Long categoryId, @PathVariable(name = "bookId") Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(categoryService.getCategoryBook(categoryId, bookId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{categoryId}/books/{bookId}")
    public ResponseEntity<Void> createCategoryBookRelation(@PathVariable(name = "categoryId") Long categoryId, @PathVariable(name = "bookId") Long bookId){
        try{
            categoryService.createCategoryBookRelation(categoryId,bookId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}/books/{bookId}")
    public ResponseEntity<Void> deleteCategoryBookRelation(@PathVariable(name = "categoryId") Long categoryId,@PathVariable(name="bookId") Long bookId) {
        try{
            categoryService.deleteCategoryBookRelation(categoryId,bookId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
