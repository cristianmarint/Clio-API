/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:01
 */

package com.biblioteca.demeter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;
    private Integer numberOfBooks;
    private String dateOfBirth;
    private String dateOfDeath;
}
