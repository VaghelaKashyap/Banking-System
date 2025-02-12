package com.example.BankingSystem.Services;

import com.example.BankingSystem.DTO.Response;
import com.example.BankingSystem.Entity.Accounts;
import com.example.BankingSystem.Entity.Transection;
import com.example.BankingSystem.Entity.Type;
import com.example.BankingSystem.Repository.AccountsRepo;
import com.example.BankingSystem.Repository.TransectionRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransectionServices {

    @Autowired
    private TransectionRepo transectionRepo;

    @Autowired
    private AccountsRepo accountsRepo;

    @Autowired
    private SmsServices smsServices;

    // Method to store transection data in table
    public void Record(Accounts account, Type type, Double Amount){
        Transection transection = new Transection();
        transection.setAccounts(account);
        transection.setType(type);
        transection.setAmount(Amount);
        transection.setDate(LocalDateTime.now());
        transectionRepo.save(transection);
    }

    // Deposit Service
    public Response Deposit(Long accountNumber, Double amount){
        Response response = new Response();
        try{
            Accounts account = accountsRepo.findById(accountNumber).get();
            String lastFour = StringUtils.right(String.valueOf(accountNumber),4);
            String Message = "Credited INR " + String.valueOf(amount) + " to A/c X" + lastFour +" on " + String.valueOf(LocalDate.now()) + " - Children Bank";
            account.setBalance(account.getBalance() + amount);
            Record(account,Type.Deposit,amount);
            accountsRepo.save(account);

            smsServices.sendSms( "+91"+String.valueOf(account.getMobileNumber()), Message);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Amount Added");
            return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Withdraw Service
    public Response Withdraw(Long accountNumber,Double amount){
        Response response = new Response();
        try {
            Accounts account = accountsRepo.findById(accountNumber).get();
            if (account.getBalance() < amount){
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Insufficient Account Balance");
                return response;
            }

            String lastFour = StringUtils.right(String.valueOf(accountNumber),4);
            String Message = "Debited INR " + String.valueOf(amount) + " to A/c X" + lastFour +" on " + String.valueOf(LocalDate.now()) + " - Children Bank";

            account.setBalance(account.getBalance()-amount);
            Record(account,Type.Withdraw,amount);
            accountsRepo.save(account);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Amount Withdraw Successfully");
            smsServices.sendSms( "+91"+String.valueOf(account.getMobileNumber()),Message);
            return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Transfer Money Service
    public Response Transfer(Long fromMobileNumber, Long toMobileNumber,Double amount){
        Response response = new Response();
        try {
            Accounts fromAccount = accountsRepo.findByMobileNumber(fromMobileNumber);
            Accounts toAccount = accountsRepo.findByMobileNumber(toMobileNumber);
            if (fromAccount.getBalance() < amount){
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Insufficient Account Balance");
                return response;
            }
            fromAccount.setBalance(fromAccount.getBalance()-amount);
            Record(fromAccount,Type.Withdraw,amount);
            accountsRepo.save(fromAccount);
            String lastFour = StringUtils.right(String.valueOf(fromAccount),4);
            String Message = "Debited INR " + String.valueOf(amount) + " to A/c X" + lastFour +" on " + String.valueOf(LocalDate.now()) + " - Children Bank";
            smsServices.sendSms( "+91"+String.valueOf(fromAccount.getMobileNumber()), Message);

            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Amount Transferred Successfully");

            toAccount.setBalance(toAccount.getBalance() + amount);
            Record(toAccount,Type.Deposit,amount);
            accountsRepo.save(toAccount);
            String lastFour2 = StringUtils.right(String.valueOf(toAccount),4);
            String Message2 = "Credited INR " + String.valueOf(amount) + " to A/c X" + lastFour2 +" on " + String.valueOf(LocalDate.now()) + " - Children Bank";
            smsServices.sendSms( "+91"+String.valueOf(toAccount.getMobileNumber()), Message2);

            return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
