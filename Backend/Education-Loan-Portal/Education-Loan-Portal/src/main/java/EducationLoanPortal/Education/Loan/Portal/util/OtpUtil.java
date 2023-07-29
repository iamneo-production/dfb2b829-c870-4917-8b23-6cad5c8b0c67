package EducationLoanPortal.Education.Loan.Portal.util;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Component;
//import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

import java.util.Random;

import static java.util.Base64.*;

@Component
public class OtpUtil {
    public String generateOtp(){
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String output = Integer.toString(randomNumber);
        while(output.length()<6){
            output="0"+output;
        }
        return output;
    }


//    public String generateEncryptionKey() {
//        SecureRandom secureRandom = new generateOtp();
//        int keyLength = 20;
//        byte[] keyBytes = new byte[keyLength];
//        secureRandom.nextBytes(keyBytes);
//
//        byte[] base64Bytes = Base64.encodeBase64URLSafe(keyBytes);
//        return StringUtils.newStringUtf8(base64Bytes);
//    }

}
