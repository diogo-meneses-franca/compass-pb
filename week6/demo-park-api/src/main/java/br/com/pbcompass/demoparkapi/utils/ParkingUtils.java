package br.com.pbcompass.demoparkapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    public static String generateInvoice(){
        LocalDateTime dateTime = LocalDateTime.now();
        String invoiceNumber = dateTime.toString().substring(0,19);
        invoiceNumber.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
        return invoiceNumber;
    }
}
