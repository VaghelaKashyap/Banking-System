package com.example.BankingSystem.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

//Entity For Transection History
@Entity
public class Transection {

    @Id
    @Column(name = "transection_id",nullable = false,unique = true)
    private long transectionId = g10digit();

    @ManyToOne
    private Accounts accounts;

    @Column(name = "transection_type",nullable = false)
    private Type type;

    @Column(name = "transection_amount")
    private double amount;

    @Column(name = "transection_date",nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;

    // Unique Transection id Generator
    public static long g10digit(){
        UUID num = UUID.randomUUID();
        long leastSignificantBites = num.getLeastSignificantBits();

        long positiveNum = Math.abs(leastSignificantBites);

        return positiveNum % 10000000000L;
    }

    // Getter Setter


    public long getTransectionId() {
        return transectionId;
    }

    public void setTransectionId(long transectionId) {
        this.transectionId = transectionId;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

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
}
