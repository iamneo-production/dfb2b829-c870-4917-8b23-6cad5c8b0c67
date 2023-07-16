package EducationLoanPortal.Education.Loan.Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Message sent successfully");
        } catch (MailException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMailWithAttachment(String to, String subject, String body, byte[] attachmentBytes, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Attach the PDF file
            InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
            helper.addAttachment(attachmentName, attachmentSource);

            mailSender.send(message);
            System.out.println("Message with attachment sent successfully");
        } catch (MailException | MessagingException e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
