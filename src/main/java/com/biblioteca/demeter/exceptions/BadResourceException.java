/*
 * @Author: cristianmarint
 * @Date: 15/12/20 13:25
 */

package com.biblioteca.demeter.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BadResourceException  extends Exception{
    private List<String> errorMessages = new ArrayList<>();

    public BadResourceException() {
    }

    public BadResourceException(String msg) {
        super(msg);
    }
}
