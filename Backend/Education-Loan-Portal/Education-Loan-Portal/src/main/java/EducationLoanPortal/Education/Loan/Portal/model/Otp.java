package EducationLoanPortal.Education.Loan.Portal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
public class Otp implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;

    private boolean active;


    private String otp;
    private LocalDateTime otpGeneratedTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }

    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }


    public Otp(Long id, String firstName, String lastName, String email, String password, String address, String phoneNumber, boolean active, String otp, LocalDateTime otpGeneratedTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.otp = otp;
        this.otpGeneratedTime = otpGeneratedTime;
    }
    public Otp() {
        super();
    }

    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", active=" + active +
                ", otp='" + otp + '\'' +
                ", otpGeneratedTime=" + otpGeneratedTime +
                '}';
    }
}


