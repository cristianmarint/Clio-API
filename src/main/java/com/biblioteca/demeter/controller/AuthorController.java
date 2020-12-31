/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:17
 */

package com.biblioteca.demeter.controller;

import com.biblioteca.demeter.dto.AuthorDto;
import com.biblioteca.demeter.exceptions.BadRequestException;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable @Min(1) Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthor(id));
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(authorService.createAuthor(authorDto));
        }catch (BadRequestException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable @Min(1) Long id, @RequestBody @Valid AuthorDto authorDto) throws ResourceNotFoundException{
        try{
            authorService.updateAuthor(id,authorDto);
            return ResponseEntity.ok().build();
        }catch (ResourceNotFoundException | BadRequestException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable @Min(1) Long id){
        try{
            authorService.deleteAuthor(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
