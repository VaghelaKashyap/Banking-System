package com.example.BankingSystem.DTO;

import org.springframework.http.HttpStatus;

//Error Handling
public class Response {
    private HttpStatus httpStatus;
    private String message;

    //Getter Setter

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
