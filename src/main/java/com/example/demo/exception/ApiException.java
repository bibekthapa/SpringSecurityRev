package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
    private final HttpStatus status;

    public HttpStatus getHttpStatus() {
		return this.status;
	}


    public ApiException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
    

}
