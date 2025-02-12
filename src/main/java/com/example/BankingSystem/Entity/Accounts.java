package com.example.BankingSystem.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Accounts {

    @Id
    @Column(name = "account_number")
    private long accountNumber = g10digit();

    @Column(name = "date_of_opening",nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date = LocalDate.now();

    @Column(name = "account_holder_name",nullable = false)
    private String name;

    @Column(name = "City",nullable = false)
    private String city;

    @Column(name = "mobile_number",unique = true,nullable = false)
    private long mobileNumber;

    @Column(name = "email",unique = true,nullable = false)
    private String email;


    @Column(name = "balance")
    private double balance;

    // Unique 10 digit account number generator
    public static long g10digit(){
        UUID num = UUID.randomUUID();
        long leastSignificantBites = num.getLeastSignificantBits();

        long positiveNum = Math.abs(leastSignificantBites);

        return positiveNum % 10000000000L;
    }


    // Getter Setter
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
