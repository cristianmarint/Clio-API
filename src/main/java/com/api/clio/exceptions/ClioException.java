/*
 * @Author: cristianmarint
 * @Date: 8/12/20 11:54
 */

package com.api.clio.exceptions;

public class ClioException extends RuntimeException{
    public ClioException(String mensajeException, Exception exception){
        super(mensajeException,exception);
    }

    public ClioException(String mensajeException){
        super(mensajeException);
    }
}
