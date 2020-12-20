/*
 * @Author: cristianmarint
 * @Date: 9/12/20 9:10
 */

package com.biblioteca.demeter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}
