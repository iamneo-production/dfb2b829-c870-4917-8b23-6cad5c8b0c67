package EducationLoanPortal.Education.Loan.Portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Loan loan;



    private Double amount;
    private LocalDate paymentDate;

    public Payment() {
        // constructor
    }

    public Payment(Loan loan, Double amount, LocalDate paymentDate) {
        this.loan = loan;
        this.amount = amount;
        this.paymentDate = paymentDate;
//        this.loanId = loan.getId(); // set loanId to the id of the loan object
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoant(Loan loan) {
        this.loan = loan;
//        this.loanId = loan.getId(); // set loanId to the id of the loan object
    }

//    public Long getLoanId() {
//        return loanId;
//    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

//    public void setLoanId(Long loanId) {
//        this.loanId = loanId;
//    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", loan=" + loan +
                ", loanId=" + loan.getId() +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}