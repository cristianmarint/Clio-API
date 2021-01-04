/*
 * @Author: cristianmarint
 * @Date: 1/1/21 19:20
 */

package com.api.clio.controller;

import com.api.clio.dto.AuthorDto;
import com.api.clio.dto.BookDto;
import com.api.clio.dto.CategoryDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Slf4j
@AllArgsConstructor
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getAllBooks());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable(name = "bookId") Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getBook(bookId));
        } catch (BadRequestException exception) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto){
        try{
            bookService.createBook(bookDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable(name = "bookId") Long bookId,@Valid @RequestBody BookDto bookDto){
        try{
            bookService.updateBook(bookId, bookDto);
            return ResponseEntity.ok().build();
        } catch (BadRequestException exception) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "bookId") Long bookId){
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException exception) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookId}/categories")
    public ResponseEntity<List<CategoryDto>> getAllBookCategories(@PathVariable(name = "bookId") Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getAllBookCategories(bookId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getBookCategory(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "categoryId") Long categoryId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getBookCategory(bookId,categoryId));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Void> createBookCategoryRelation(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "categoryId") Long categoryId){
        try{
            bookService.createBookCategoryRelation(bookId,categoryId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Void> deleteBookCategoryRelation(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "categoryId") Long categoryId){
        try{
            bookService.deleteBookCategoryRelation(bookId,categoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{bookId}/authors")
    public ResponseEntity<List<AuthorDto>> getAllBookAuthors(@PathVariable(name = "bookId") Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getAllBookAuthors(bookId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<AuthorDto> getBookAuthor(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "authorId") Long authorId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getBookAuthor(bookId,authorId));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> createBookAuthorRelation(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "authorId") Long authorId){
        try{
            bookService.createBookAuthorRelation(bookId,authorId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> deleteBookAuthorRelation(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "authorId") Long authorId){
        try{
            bookService.deleteBookAuthorRelation(bookId,authorId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
