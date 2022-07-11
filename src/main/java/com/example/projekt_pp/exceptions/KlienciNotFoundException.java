package com.example.projekt_pp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such order")
public class KlienciNotFoundException extends RuntimeException{

    public KlienciNotFoundException(long id){
        super("Klient o id "+id+" nie istnieje");
    }
}
