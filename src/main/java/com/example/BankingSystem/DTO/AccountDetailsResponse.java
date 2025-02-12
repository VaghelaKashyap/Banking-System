package com.example.BankingSystem.DTO;

import com.example.BankingSystem.Entity.Accounts;
import org.springframework.http.HttpStatus;

// Error Handling For Account Details
public class AccountDetailsResponse {
    private Accounts accounts;
    private String message;
    private HttpStatus httpStatus;

    // Getter Setter
    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
