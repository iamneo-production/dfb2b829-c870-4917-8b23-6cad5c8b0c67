package EducationLoanPortal.Education.Loan.Portal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify Account");
        mimeMessageHelper.setText(
                "Your OTP for verification is: " + otp,
                true
        );
        javaMailSender.send(mimeMessage);
    }

    public void sendSetPasswordEmail(String email, String token) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set Password");

        // Construct the password reset link with the token
        String setPasswordLink = "http://localhost:4200/set-password?token=" + token + "&email=" + email;

        mimeMessageHelper.setText(
                "<div>" +
                        "<a href=\"" + setPasswordLink + "\" target=\"_blank\">Click here to set password</a>" +
                        "</div>",
                true
        );

        javaMailSender.send(mimeMessage);
    }
}

