package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ApiError extends RuntimeException{

    private HttpStatus status;

    private String message;

   

    ApiError(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

     public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    
}
