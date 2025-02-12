package com.example.BankingSystem.DTO;
import com.example.BankingSystem.Entity.Type;
import java.time.LocalDateTime;

// DTO Class For Transection Input
public class TransectionDto {
    private Type type;
    private double amount;
    private LocalDateTime date;
    private Long acNum;

    // Getter Setter
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getAcNum() {
        return acNum;
    }

    public void setAcNum(Long acNum) {
        this.acNum = acNum;
    }
}
