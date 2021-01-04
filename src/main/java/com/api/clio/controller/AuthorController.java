/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:17
 */

package com.api.clio.controller;

import com.api.clio.dto.AuthorDto;
import com.api.clio.dto.BookDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Slf4j
@AllArgsConstructor
@Validated
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable(name = "authorId") @Min(1) Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(authorService.getAuthor(bookId));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        try{
            authorService.createAuthor(authorDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{authorId}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable(name = "authorId") @Min(1) Long authorId, @RequestBody @Valid AuthorDto authorDto){
        try{
            authorService.updateAuthor(authorId,authorDto);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable(name = "authorId") @Min(1) Long id){
        try{
            authorService.deleteAuthor(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<BookDto>> getAllAuthorBooks(@PathVariable(name = "authorId") Long authorId){
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(authorService.getAllAuthorBooks(authorId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<BookDto> getAuthorBook(@PathVariable(name="authorId") Long authorId, @PathVariable(name = "bookId") Long bookId){
        try{
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(authorService.getAuthorBook(authorId, bookId));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> createAuthorBookRelation(@PathVariable(name = "authorId") Long authorId, @PathVariable(name = "bookId") Long bookId){
        try{
            authorService.createAuthorBookRelation(authorId,bookId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> deleteAuthorBookRelation(@PathVariable(name = "authorId") Long authorId,@PathVariable(name="bookId") Long bookId) {
        try{
            authorService.deleteAuthorBookRelation(authorId,bookId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
