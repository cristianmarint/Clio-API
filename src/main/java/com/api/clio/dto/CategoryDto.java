/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:53
 */

package com.api.clio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfBooks;
}
