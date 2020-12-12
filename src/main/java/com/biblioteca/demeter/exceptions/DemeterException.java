/*
 * @Author: cristianmarint
 * @Date: 8/12/20 11:54
 */

package com.biblioteca.demeter.exceptions;

public class DemeterException extends RuntimeException{
    public DemeterException(String mensajeException, Exception exception){
        super(mensajeException,exception);
    }

    public DemeterException(String mensajeException){
        super(mensajeException);
    }
}
