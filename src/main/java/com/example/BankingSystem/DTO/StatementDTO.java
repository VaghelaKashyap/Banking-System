package com.example.BankingSystem.DTO;

import com.example.BankingSystem.Entity.Accounts;
import com.example.BankingSystem.Entity.Type;

import java.time.LocalDateTime;

// Statement Entity
public class StatementDTO {
    private Accounts accounts;
    private Type type;
    private Double amount;
    private LocalDateTime date;

    // Getter Setter
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
}
