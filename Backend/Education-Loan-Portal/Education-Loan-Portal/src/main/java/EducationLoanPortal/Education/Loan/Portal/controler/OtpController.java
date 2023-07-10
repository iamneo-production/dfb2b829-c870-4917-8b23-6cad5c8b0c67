package EducationLoanPortal.Education.Loan.Portal.controler;

import EducationLoanPortal.Education.Loan.Portal.model.Otp;
import EducationLoanPortal.Education.Loan.Portal.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/otp")
public class OtpController {
    private final OtpService otpService;
    @Autowired
    public OtpController (OtpService otpService){
        this.otpService=otpService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Otp otp){

        return new ResponseEntity<>(otpService.register(otp) , HttpStatus.OK);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(otpService.verifyAccount(email,otp),HttpStatus.OK);
    }

    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return new ResponseEntity<>(otpService.regenerateOtp(email), HttpStatus.OK);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(otpService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword) throws MessagingException {
        return new ResponseEntity<>(otpService.setPassword(email,newPassword), HttpStatus.OK);
    }

}
