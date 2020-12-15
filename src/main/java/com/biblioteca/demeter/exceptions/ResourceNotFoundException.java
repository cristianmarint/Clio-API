/*
 * @Author: cristianmarint
 * @Date: 15/12/20 11:16
 */

package com.biblioteca.demeter.exceptions;

public class ResourceNotFoundException  extends Exception{
    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
