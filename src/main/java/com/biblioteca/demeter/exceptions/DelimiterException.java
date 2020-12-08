/*
 * @Author: cristianmarint
 * @Date: 8/12/20 11:54
 */

package com.biblioteca.demeter.exceptions;

public class DelimiterException extends RuntimeException{
    public DelimiterException(String mensajeException, Exception exception){
        super(mensajeException,exception);
    }

    public DelimiterException(String mensajeException){
        super(mensajeException);
    }
}
