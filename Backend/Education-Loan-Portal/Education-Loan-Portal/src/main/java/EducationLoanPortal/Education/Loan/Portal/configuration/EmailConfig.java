package EducationLoanPortal.Education.Loan.Portal.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private String mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl JavaMailSender = new JavaMailSenderImpl();
        JavaMailSender.setHost(mailHost);
        JavaMailSender.setPort(Integer.parseInt(mailPort));
        JavaMailSender.setUsername(mailUsername);
        JavaMailSender.setPassword(mailPassword);

        Properties props = JavaMailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable","true");
        return JavaMailSender;

    }

}
