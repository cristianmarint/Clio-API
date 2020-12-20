/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:14
 */

package com.biblioteca.demeter.service;

import com.biblioteca.demeter.dto.AuthorResponse;
import com.biblioteca.demeter.exceptions.ResourceNotFoundException;
import com.biblioteca.demeter.mapper.AuthorMapper;
import com.biblioteca.demeter.model.Author;
import com.biblioteca.demeter.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorResponse> getAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthor(Long id) throws ResourceNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Author with "+id+" was not found"));
        return authorMapper.mapAuthorToDto(author);
    }
}
