package com.example.BankingSystem.Services;
import org.springframework.web.multipart.MultipartFile;

public interface EmailServices {
    String Send(MultipartFile[] file,String to, String subject, String body);
}
