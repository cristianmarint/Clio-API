/*
 * @Author: cristianmarint
 * @Date: 15/12/20 11:16
 */

package com.api.clio.exceptions;

public class ResourceNotFoundException  extends Exception{
    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String message){
        super(message);
    }
    public ResourceNotFoundException(Long id,String type){
        super(type+" with ID "+id+" was not found");
    }
}
