package com.example.BankingSystem.Controller;

import com.example.BankingSystem.DTO.AccountDetailsResponse;
import com.example.BankingSystem.DTO.Response;
import com.example.BankingSystem.Entity.Accounts;
import com.example.BankingSystem.Repository.AccountsRepo;
import com.example.BankingSystem.Services.AccountServices;
import com.example.BankingSystem.Services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/customer")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private AccountsRepo accountsRepo;

    @Autowired
    private EmailServices emailServices;

    // Open Account API
    @PostMapping("/open_account")
    public Response openAccount(@RequestBody Accounts account) {
        return accountServices.openAccount(account);
    }

    // Get Account Details API
    @GetMapping("/show_account_details")
    public AccountDetailsResponse getAccountDetails(@RequestParam Long AccountNumber) {
        return accountServices.getAccountDetails(AccountNumber);
    }
    // Statement Controller
    @GetMapping("/get_account_statement")
    public Response getStatement(@RequestParam Long accountNumber) {
        String filePath = "E:\\statement.csv";
        Response response = accountServices.getStatement(accountNumber, filePath);
        try {
            if (response.getHttpStatus() == HttpStatus.CREATED) {
                File csv = new File(filePath);
                MultipartFile[] attachment = new MultipartFile[1];
                attachment[0] = new MockMultipartFile("statement.csv", new FileInputStream(csv));
                String subject = "Bank Statement";
                String Body = "Here Is Your Bank Statement, Thank You For Connecting With Us!";
                String TO = accountsRepo.findById(accountNumber).get().getEmail();
                emailServices.Send(attachment, TO, subject, Body);
            }
                response.setMessage("Statement Sent");
                response.setHttpStatus(HttpStatus.OK);
                return response;
        }catch (Exception e){
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Update Name API
    @PutMapping("/update_name")
    public Response updateName(@RequestParam Long AccountNumber,@RequestParam String Name){
        return accountServices.updateName(AccountNumber,Name);
    }

    // Update Mobile Number API
    @PutMapping("/update_mobile_number")
    public Response updateMobileNumber(@RequestParam Long accountNumber,@RequestParam Long Number){
        return accountServices.updateMobileNumber(accountNumber,Number);
    }

    // Close Account API
    @DeleteMapping("/close_account")
    public Response closeAccount(@RequestParam Long accountNumber){
       return accountServices.closeAccount(accountNumber);
    }
}
