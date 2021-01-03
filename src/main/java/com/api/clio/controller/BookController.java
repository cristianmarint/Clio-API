/*
 * @Author: cristianmarint
 * @Date: 1/1/21 19:20
 */

package com.api.clio.controller;

import com.api.clio.dto.BookDto;
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

//    /api/books/{id}
    /**
     * @return ResponseEntity<List<BookDto>>
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bookService.getAllBooks());
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param bookId
     * @return
     */
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

    /**
     * @param bookDto
     * @return
     */
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
    public ResponseEntity<Void> updateBook(@PathVariable(name = "bookId") Long bookId, @RequestBody BookDto bookDto){
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

}
