package EducationLoanPortal.Education.Loan.Portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

import java.io.Serializable;

@Entity
public class LoanApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private User user;

    private Double loanAmount;
    private String purpose;
    private String status;
    private LocalDate applicationDate;
    private Long user_id;

    public LoanApplication() {
        // constructor
    }

    public LoanApplication(User user, Double loanAmount, String purpose, String status, LocalDate applicationDate) {
        this.user = user;
        this.loanAmount = loanAmount;
        this.purpose = purpose;
        this.status = status;
        this.applicationDate = applicationDate;
        this.user_id = user.getId(); // set user_id to the id of the user object
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.user_id = user.getId(); // set user_id to the id of the user object
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "id=" + id +
                ", user=" + user +
                ", loanAmount=" + loanAmount +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", applicationDate=" + applicationDate +
                ", user_id=" + user_id +
                '}';
    }


}