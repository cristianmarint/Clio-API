/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:14
 */

package com.api.clio.service;

import com.api.clio.dto.AuthorDto;
import com.api.clio.exceptions.BadRequestException;
import com.api.clio.exceptions.ResourceNotFoundException;
import com.api.clio.mapper.AuthorMapper;
import com.api.clio.model.Author;
import com.api.clio.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Validated
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthor(@Min(1) Long id) throws ResourceNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id,"Author"));
        return authorMapper.mapAuthorToDto(author);
    }

    public AuthorDto createAuthor(@Valid AuthorDto authorDto) throws BadRequestException {
        validateAuthor(authorDto);
        Author author = authorRepository.save(authorMapper.mapDtoToAuthor(authorDto));
        authorDto.setId(author.getId());
        return authorDto;
    }

    @Transactional
    public void updateAuthor(@Min(1) Long id, @Valid AuthorDto authorDto) throws BadRequestException,ResourceNotFoundException{
        validateAuthor(authorDto);
        if (authorRepository.findById(id).isPresent()){
            authorDto.setId(id);
            authorRepository.save(authorMapper.mapDtoToAuthor(authorDto));
        }else{
            throw new ResourceNotFoundException(id,"Author");
        }
    }

    public void deleteAuthor(@Min(1) Long id) throws ResourceNotFoundException {
        if (authorRepository.findById(id).isPresent()){
            authorRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException(id,"Author");
        }
    }

    public void validateAuthor(AuthorDto authorDto) throws BadRequestException{
        if (authorDto == null) {
            throw new BadRequestException("Author cannot be null");
        }else if (authorDto.getName() == null){
            throw new BadRequestException("Author name cannot be null");
        }
    }
}
