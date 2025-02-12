package com.example.BankingSystem.Controller;

import com.example.BankingSystem.DTO.Response;
import com.example.BankingSystem.Services.TransectionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class TransectionController {

    @Autowired
    private TransectionServices transectionServices;

    @PutMapping("/deposit")
    public Response deposit(@RequestParam Long accountNumber,@RequestParam Double amount){
        return transectionServices.Deposit(accountNumber,amount);
    }

    @PutMapping("/withdraw")
    public Response withdraw(@RequestParam Long accountNumber,@RequestParam Double amount){
        return transectionServices.Withdraw(accountNumber,amount);
    }

    @PutMapping("/transfer")
    public Response transfer(@RequestParam Long fromAccountNumber,@RequestParam Long toAccountNumber,@RequestParam Double amount){
        return transectionServices.Transfer(fromAccountNumber,toAccountNumber,amount);
    }
}
