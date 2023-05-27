package EducationLoanPortal.Education.Loan.Portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    private Long user_id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnoreProperties("loanList")
    private User user;

    private Double loanAmount;
    private Double interestRate;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    public Loan() {
        // constructor
    }

    public Loan(User user, Double loanAmount, Double interestRate, String status, LocalDate startDate,
            LocalDate endDate) {
        this.user = user;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }


}
