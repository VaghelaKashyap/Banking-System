package com.example.BankingSystem.Services;
import com.example.BankingSystem.DTO.AccountDetailsResponse;
import com.example.BankingSystem.DTO.Response;
import com.example.BankingSystem.DTO.StatementDTO;
import com.example.BankingSystem.Entity.Accounts;
import com.example.BankingSystem.Entity.Transection;
import com.example.BankingSystem.Repository.AccountsRepo;
import com.example.BankingSystem.Repository.TransectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

// Services to open account, get account details
@Service
public class AccountServices {

    @Autowired
    private AccountsRepo accountsRepo;

    @Autowired
    private TransectionRepo transectionRepo;

    @Autowired
    private SmsServices smsServices;

    // Open Account
    public Response openAccount(Accounts account){
        Response response = new Response();
        try{
            Accounts a = accountsRepo.save(account);
            String number = "+91"+String.valueOf(a.getMobileNumber());
            String Message = "Welcome to Children Bank Of Surat.\nMr/Mrs." + a.getName() + "\nYour Account Number : "+ String.valueOf(a.getAccountNumber()) + "\n- Children Bank Of Surat";
            smsServices.sendSms(number,Message);
            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("Account Successfully Opened. Your Account Number: " + String.valueOf(a.getAccountNumber()));
            return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Get Account Details
   public AccountDetailsResponse getAccountDetails(Long AccountNumber){
        AccountDetailsResponse response =  new AccountDetailsResponse();
        try {
        Accounts account = accountsRepo.findById(AccountNumber).get();
        response.setAccounts(account);
        response.setMessage("Here is your account details");
        response.setHttpStatus(HttpStatus.OK);
        return response;
        }catch (Exception e){
         response.setAccounts(null);
         response.setMessage("Something Went Wrong " + e.getMessage());
         response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
         return response;
        }
    }

    // Statement Service
    public Response getStatement(Long AccountNumber , String filePath) {
        Response response = new Response();
        List<Transection> statement = transectionRepo.findAll();
        List<StatementDTO> list = new ArrayList<>();
        for (int i = 0; i < statement.size(); i++) {
            Transection t = statement.get(i);
            Accounts a = t.getAccounts();
            if (a.getAccountNumber() != AccountNumber) {
                continue;
            }
            StatementDTO sd = new StatementDTO();
            sd.setAccounts(t.getAccounts());
            sd.setAmount(t.getAmount());
            sd.setType(t.getType());
            sd.setDate(t.getDate());
            list.add(sd);
        }
        try (Writer writer = new FileWriter(filePath);
             ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(writer, CsvPreference.EXCEL_PREFERENCE)) {

            String[] headers = {"accounts_account_number", "amount", "type", "date"};
            csvBeanWriter.writeHeader(headers);

            for (StatementDTO s : list) {
                csvBeanWriter.write(s, headers);
            }
            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("CSV File Created");
            return response;

        } catch (IOException e) {
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

        // Update Name
    public Response updateName(Long accountNumber, String newName){
        Response response = new Response();
        try{
            Accounts account = accountsRepo.findById(accountNumber).get();
            account.setName(newName);
            accountsRepo.save(account);
            response.setMessage("Name Updated");
            response.setHttpStatus(HttpStatus.CREATED);
            String Number  =  "+91"+String.valueOf(account.getMobileNumber());
            String Message = "Mr/Mrs. " + newName + " Your name is updated successfully\n - Children Bank Of Surat";
            smsServices.sendSms(Number,Message);
            return response;
        }catch (Exception e){
            response.setMessage("Something Went Wrong " + e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    // Update Mobile Number
    public Response updateMobileNumber(Long accountNumber, Long newMobileNumber){
        Response response = new Response();
        try{
            Accounts account = accountsRepo.findById(accountNumber).get();
            account.setMobileNumber(newMobileNumber);
            accountsRepo.save(account);
            response.setMessage("Mobile Number Updated Updated");
            response.setHttpStatus(HttpStatus.CREATED);
            String Number  =  "+91"+String.valueOf(account.getMobileNumber());
            String Message = "Mr/Mrs. " + account.getName() + " Your Mobile Number is updated successfully\n - Children Bank Of Surat";
            smsServices.sendSms(Number,Message);
            return response;
        }catch (Exception e){
            response.setMessage("Something Went Wrong " + e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    // Delete Account ("diffAccountNumber is used to transfer balance to other bank account")
    public Response closeAccount(Long accountNumber){
        Response response = new Response();
        try {
            Accounts account = accountsRepo.findById(accountNumber).get();
            String Number  =  "+91"+String.valueOf(account.getMobileNumber());
            String Message = "See You Again Mr/Mrs. " + account.getName() + "\nYour Account Closed Successfully\n - Children Bank Of Surat";
            smsServices.sendSms(Number,Message);
            accountsRepo.delete(account);
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("See You Again!, Your account is closed successfully");
            return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
