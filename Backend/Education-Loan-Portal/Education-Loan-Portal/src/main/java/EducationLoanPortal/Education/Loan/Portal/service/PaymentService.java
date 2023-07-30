package EducationLoanPortal.Education.Loan.Portal.service;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.exception.UserNotFoundException;
import EducationLoanPortal.Education.Loan.Portal.model.Loan;
import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import EducationLoanPortal.Education.Loan.Portal.repository.LoanRepo;
import EducationLoanPortal.Education.Loan.Portal.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private  final PaymentRepo paymentRepo;

    private final LoanRepo loanRepo;

    private final MailService mailService;

    @Autowired
    public PaymentService(MailService mailService,LoanRepo loanRepo, PaymentRepo paymentRepo) {
        this.mailService = mailService;
        this.paymentRepo=paymentRepo;
        this.loanRepo=loanRepo;

        // Other constructor logic
    }
    public List<Payment> getPaymentsByLoanId(Long loan_id){
        return paymentRepo.findByLoanId(loan_id);
    }
    public Payment createPayment(Payment payment) {
        Payment createdPayment = paymentRepo.save(payment);

        // Retrieve loan details
        Long loanId = payment.getLoan_id();
        Optional<Loan> loanOptional = loanRepo.findById(loanId);
        if (loanOptional.isEmpty()) {
            throw new RuntimeException("Loan not found with id: " + loanId);
        }
        Loan loan = loanOptional.get();

        // Get user email for recipient
        String userEmail = loan.getUser().getEmail();

        // Compose email content
        String subject = "Payment Created";
        String body = "Payment has been successfully created.\n\n" +
                "Payment Details:\n" +
                "Loan ID: " + createdPayment.getLoan_id() + "\n" +
                "Payment Amount: " + createdPayment.getAmount() + "\n" +
                "Payment Date: " + createdPayment.getPaymentDate() + "\n" ;


        // Send email
        mailService.sendMail(userEmail, subject, body);

        return createdPayment;
    }


    public Payment getPaymentById(Long id) throws ResourceNotFoundException{
        Optional<Payment> payment = paymentRepo.getPaymentById(id);
        if (payment.isEmpty()){
            throw new ResourceNotFoundException("Payment not fount with payment id: "+id);
        }
        else {
            return payment.get();
        }
    }

    public Payment updatePaymentById(Long id, Payment updatedPayment) {
        Optional<Payment> existingPayment = paymentRepo.findById(id);
        if (!existingPayment.isPresent()) {
            throw new RuntimeException("Payment not found with this id: " + id);
        } else {
            Payment payment = existingPayment.get();

            payment.setAmount(updatedPayment.getAmount());
            payment.setPaymentDate(updatedPayment.getPaymentDate());
            Payment savedPayment = paymentRepo.save(payment);

            // Retrieve loan details
            Long loanId = savedPayment.getLoan_id();
            Optional<Loan> loanOptional = loanRepo.findById(loanId);
            if (loanOptional.isEmpty()) {
                throw new RuntimeException("Loan not found with id: " + loanId);
            }
            Loan loan = loanOptional.get();

            // Get user email for recipient
            String userEmail = loan.getUser().getEmail();

            // Compose email content
            String subject = "Payment Updated";
            String body = "Payment has been successfully updated.\n\n" +
                    "Payment Details:\n" +
                    "Loan ID: " + savedPayment.getLoan_id() + "\n" +
                    "Payment Amount: " + savedPayment.getAmount() + "\n" +
                    "Payment Date: " + savedPayment.getPaymentDate() + "\n";

            // Send email
            mailService.sendMail(userEmail, subject, body);

            return savedPayment;
        }
    }


    public Payment updatePaymentStatusById(Long id, Payment updatedPayment) {
        Optional<Payment> existingPayment = paymentRepo.findById(id);
        if (!existingPayment.isPresent()) {
            throw new RuntimeException("Payment not found with this id: " + id);
        } else {
            Payment payment = existingPayment.get();
            payment.setStatus("Completed");
            Payment savedPayment = paymentRepo.save(payment);

            // Retrieve loan details
            Long loanId = savedPayment.getLoan_id();
            Optional<Loan> loanOptional = loanRepo.findById(loanId);
            if (loanOptional.isEmpty()) {
                throw new RuntimeException("Loan not found with id: " + loanId);
            }
            Loan loan = loanOptional.get();

            // Get user email for recipient
            String userEmail = loan.getUser().getEmail();

            // Compose email content
            String subject = "Payment Status Updated";
            String body = "Payment status has been successfully updated.\n\n" +
                    "Payment Details:\n" +
                    "Loan ID: " + savedPayment.getLoan_id() + "\n" +
                    "Payment Amount: " + savedPayment.getAmount() + "\n" +
                    "Payment Date: " + savedPayment.getPaymentDate() + "\n" +
                    "Payment Status: " + savedPayment.getStatus() + "\n";

            // Send email
            mailService.sendMail(userEmail, subject, body);

            return savedPayment;
        }
    }


    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    public void deletePaymentById(Long id) {
        paymentRepo.deleteById(id);
    }

}