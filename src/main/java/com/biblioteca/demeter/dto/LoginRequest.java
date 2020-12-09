/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:28
 */

package com.biblioteca.demeter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
