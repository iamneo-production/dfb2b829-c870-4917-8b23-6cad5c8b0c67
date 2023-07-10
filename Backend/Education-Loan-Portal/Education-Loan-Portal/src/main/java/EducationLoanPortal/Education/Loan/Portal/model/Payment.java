package EducationLoanPortal.Education.Loan.Portal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonProperty("loan_id")
    private Long loan_id;



    private Double amount;
    private LocalDate paymentDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Long getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(Long loan_id) {
        this.loan_id = loan_id;
    }



    public Payment() {
        // constructor
    }

    public Payment(Loan loan, Long loan_id, Double amount, LocalDate paymentDate,String status) {

        this.loan = loan;
        this.loan_id = loan_id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.loan_id = loan.getId(); // set loanId to the id of the loan object
        this.status = status;
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

    public void setLoan(Loan loan) {
        this.loan = loan;
        this.loan_id = loan.getId(); // set loanId to the id of the loan object
    }







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
                ", loan_id=" + loan.getId() +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", status=" + status+
                '}';
    }

}
