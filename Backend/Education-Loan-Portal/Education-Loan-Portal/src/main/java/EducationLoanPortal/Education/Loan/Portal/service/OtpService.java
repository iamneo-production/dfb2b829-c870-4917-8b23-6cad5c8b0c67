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

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class OtpService {

    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final OtpRepo otpRepo;
    private final UserRepo userRepo; // Add User repository
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    @Autowired
    public OtpService(OtpUtil otpUtil, EmailUtil emailUtil, OtpRepo otpRepo, UserRepo userRepo,PasswordEncoder passwordEncoder,RoleRepo roleRepo) {
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
        this.otpRepo = otpRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    public String register(Otp otp) {
        String otp1 = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(otp.getEmail(), otp1);
        } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP. Please try again.");
        }

        // Check if the user already exists by email
        if (userRepo.existsByEmail(otp.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check if the user already exists by phone number
        if (userRepo.existsByPhoneNumber(otp.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        // Create a new Otp entity
        Otp newOtp = new Otp();
        newOtp.setFirstName(otp.getFirstName());
        newOtp.setLastName(otp.getLastName());
        newOtp.setEmail(otp.getEmail());
        newOtp.setPassword(passwordEncoder.encode(otp.getPassword()));
        newOtp.setAddress(otp.getAddress());
        newOtp.setPhoneNumber(otp.getPhoneNumber());
        newOtp.setActive(otp.isActive());
        newOtp.setOtp(otp1);
        newOtp.setOtpGeneratedTime(LocalDateTime.now());

        // Save the new Otp
        otpRepo.save(newOtp);

        return "User registration successful.";
    }

    public String verifyAccount(String email, String otp) {
        Otp newotp = otpRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email"));
        if (newotp.getOtp().equals(otp) && Duration.between(newotp.getOtpGeneratedTime(), LocalDateTime.now())
                .getSeconds() < (500)) {
            newotp.setActive(true);
            otpRepo.save(newotp);

            // Create a new User entity
            User newUser = new User();
            newUser.setFirstName(newotp.getFirstName());
            newUser.setLastName(newotp.getLastName());
            newUser.setEmail(newotp.getEmail());
            newUser.setPassword(newotp.getPassword());
            newUser.setAddress(newotp.getAddress());
            newUser.setPhoneNumber(newotp.getPhoneNumber());

            Role defaultRole = (Role) roleRepo.findByRoleName("User")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(defaultRole);

            newUser.setRole(userRoles);

            // Save the new User
            userRepo.save(newUser);

            return "Account verified. You can login now.";
        }
        return "Please regenerate OTP and try again.";
    }

    public String regenerateOtp(String email){
        Otp newotp = otpRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));

        String otp =otpUtil.generateOtp();

        try{
            emailUtil.sendOtpEmail(email ,otp);

        }
        catch(Exception e){
            throw new RuntimeException("unable to send otp please try again");
        }

        newotp.setOtp(otp);
        newotp.setOtpGeneratedTime(LocalDateTime.now());
        otpRepo.save(newotp);
        return "Email sent....  please verify account";
    }


    public String forgotPassword(String email) throws MessagingException {
        Otp newotp = otpRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));
        try{
            emailUtil.sendSetPasswordEmail(email);
        }
        catch(MessagingException e){
            throw new RuntimeException("Unable to send set password mail try again");
        }

        return "please check your mail to set new password to your account";
    }

    public String setPassword(String email, String newPassword) {
        Otp newotp = otpRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));
        newotp.setPassword(newPassword);
        otpRepo.save(newotp);
        return "New password set successfully login with new password";
    }
}

