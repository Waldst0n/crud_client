package com.waldstonsantana.crud_cliente.services.exceptions;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
