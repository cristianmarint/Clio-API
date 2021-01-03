/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:31
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
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
