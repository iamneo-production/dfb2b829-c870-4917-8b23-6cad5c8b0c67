package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.model.Otp;
import EducationLoanPortal.Education.Loan.Portal.model.Role;
import EducationLoanPortal.Education.Loan.Portal.model.User;
import EducationLoanPortal.Education.Loan.Portal.repository.OtpRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.RoleRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.UserRepo;
import EducationLoanPortal.Education.Loan.Portal.util.EmailUtil;
import EducationLoanPortal.Education.Loan.Portal.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OtpService {

    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final OtpRepo otpRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Autowired
    public OtpService(OtpUtil otpUtil, EmailUtil emailUtil, OtpRepo otpRepo, UserRepo userRepo,
                      PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.otpRepo = otpRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public String register(Otp otp) {
        String generatedOtp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(otp.getEmail(), generatedOtp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP. Please try again.");
        }

        if (UserRepo.existsByEmail(otp.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        if (userRepo.existsByPhoneNumber(otp.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists.");
        }

        Otp newOtp = new Otp();
        newOtp.setFirstName(otp.getFirstName());
        newOtp.setLastName(otp.getLastName());
        newOtp.setEmail(otp.getEmail());
        newOtp.setPassword(passwordEncoder.encode(otp.getPassword()));
        newOtp.setAddress(otp.getAddress());
        newOtp.setPhoneNumber(otp.getPhoneNumber());
        newOtp.setActive(otp.isActive());
        newOtp.setOtp(generatedOtp);
        newOtp.setOtpGeneratedTime(LocalDateTime.now());

        otpRepo.save(newOtp);

        return "User registration successful.";
    }

    public String verifyAccount(String email, String otp) {
        Otp newOtp = otpRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email."));
        if (newOtp.getOtp().equals(otp) && Duration.between(newOtp.getOtpGeneratedTime(), LocalDateTime.now())
                .getSeconds() < (500)) {
            newOtp.setActive(true);

            User newUser = new User();
            newUser.setFirstName(newOtp.getFirstName());
            newUser.setLastName(newOtp.getLastName());
            newUser.setEmail(newOtp.getEmail());
            newUser.setPassword(newOtp.getPassword());
            newUser.setAddress(newOtp.getAddress());
            newUser.setPhoneNumber(newOtp.getPhoneNumber());

            Role defaultRole = (Role) roleRepo.findByRoleName("User")
                    .orElseThrow(() -> new RuntimeException("Default role not found."));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(defaultRole);

            newUser.setRole(userRoles);

            userRepo.save(newUser);

            otpRepo.delete(newOtp);

            return "Account verified. You can log in now.";
        }

        long otpCount = otpRepo.countByEmailAndOtpGeneratedTimeBetween(email,
                LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX));
        if (otpCount >= 5) {
            return "You have reached the maximum OTP generation limit for today. Please try again tomorrow.";
        }

        return "Please regenerate OTP and try again.";
    }

    public String regenerateOtp(String email) {
        Otp existingOtp = otpRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email."));

        long otpCount = otpRepo.countByEmailAndOtpGeneratedTimeBetween(email,
                LocalDateTime.now().with(LocalTime.MIN), LocalDateTime.now().with(LocalTime.MAX));
        if (otpCount >= 5) {
            return "You have reached the maximum OTP generation limit for today. Please try again tomorrow.";
        }

        String newOtp = otpUtil.generateOtp();

        try {
            emailUtil.sendOtpEmail(email, newOtp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP. Please try again.");
        }

        existingOtp.setOtp(newOtp);
        existingOtp.setOtpGeneratedTime(LocalDateTime.now());
        otpRepo.save(existingOtp);

        return "New OTP generated and sent to your email address.";
    }
    public String forgotPassword(String email) {
        User existingUser = (User) userRepo.findByEmail(email).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("User not found with this email.");
        }

        Optional<Otp> existingOtp = otpRepo.findByEmail(email);

        // Generate a new OTP
        String generatedOtp = otpUtil.generateOtp();
        LocalDateTime now = LocalDateTime.now();

        if (!existingOtp.isPresent()) {
            // If no OTP exists, create a new one
            Otp newOtp = new Otp();
            newOtp.setEmail(email);
            newOtp.setOtp(generatedOtp);
            newOtp.setOtpGeneratedTime(now);
            otpRepo.save(newOtp);
        } else {
            // If an OTP already exists, update it
            Otp existingOtp1 = existingOtp.get();
            existingOtp1.setOtp(generatedOtp);
            existingOtp1.setOtpGeneratedTime(now);
            otpRepo.save(existingOtp1);
        }

        try {
            emailUtil.sendSetPasswordEmail(email, generatedOtp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP. Please try again.");
        }

        return "Please check your email for the OTP to reset your password.";
    }




    public String setPassword(String email, String otp, String newPassword) {
        Otp existingOtp = otpRepo.findByEmail(email).orElse(null);
        if (existingOtp == null) {
            return "Invalid or expired OTP. Please regenerate OTP and try again.";
        }

        if (!existingOtp.getOtp().equals(otp) || isOtpExpired(existingOtp)) {
            return "Invalid or expired OTP. Please regenerate OTP and try again.";
        }

        User existingUser = (User) userRepo.findByEmail(email).orElse(null);
        if (existingUser == null) {
            return "User not found with this email.";
        }


        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(existingUser);

        otpRepo.delete(existingOtp);

        return "Password reset successful. You can now log in with your new password.";
    }

    // Helper method to check if the OTP is expired
    private boolean isOtpExpired(Otp otp) {
        Duration duration = Duration.between(otp.getOtpGeneratedTime(), LocalDateTime.now());
        return duration.getSeconds() >= 500; // Replace 500 with the desired OTP expiration time in seconds
    }

}