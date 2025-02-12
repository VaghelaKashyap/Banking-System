package com.example.BankingSystem.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsServices {

    // Sms Service
    public static final String ACCOUNT_SID = "ACc66af1bb2c31006c74f17444cad3cdda";
    public static final String AUTH_TOKEN = "af8035f8407ba72615edf4591e26030a";

    public void sendSms(String smsNumber, String smsMessage) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(smsNumber), new PhoneNumber("+14066257322"),smsMessage)
                .create();
        System.out.println(message.getSid());
    }
}
