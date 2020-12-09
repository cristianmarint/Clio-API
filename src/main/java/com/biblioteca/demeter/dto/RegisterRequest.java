/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:31
 */

package com.biblioteca.demeter.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
