package com.example.BankingSystem.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsServices {

    // Sms Service
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    public void sendSms(String smsNumber, String smsMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(smsNumber), new PhoneNumber(""),smsMessage)
                .create();
        System.out.println(message.getSid());
    }
}
