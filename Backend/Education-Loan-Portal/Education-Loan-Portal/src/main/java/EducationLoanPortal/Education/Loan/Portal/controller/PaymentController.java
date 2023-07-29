package EducationLoanPortal.Education.Loan.Portal.controller;

import org.springframework.http.HttpStatus;

import EducationLoanPortal.Education.Loan.Portal.exception.ResourceNotFoundException;

import EducationLoanPortal.Education.Loan.Portal.model.Payment;
import EducationLoanPortal.Education.Loan.Portal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @GetMapping("/findByLoanId/{loan_id}")
    public ResponseEntity<List<Payment>> getPaymentsByLoanId(@PathVariable("loan_id") Long loan_id){
        List<Payment> payments = paymentService.getPaymentsByLoanId(loan_id);
        if(!payments.isEmpty()){
            return ResponseEntity.ok(payments);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment> payments = paymentService.getAllPayments();
        if(!payments.isEmpty()){
            return ResponseEntity.ok(payments);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
    }


    @PostMapping("")
    public Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    public Optional<Payment> getPaymentById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        Optional<Payment> payment = Optional.ofNullable(paymentService.getPaymentById(id));
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException("Loan not found with id: " + id);
        }
        return payment;
    }

    @PutMapping("/updateById/{id}")

    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long id, @RequestBody Payment updatedPayment) throws ResourceNotFoundException {
        Payment payment = paymentService.getPaymentById(id);
        if(payment!=null){
            Payment newPayment = paymentService.updatePaymentById(id,updatedPayment);
            return new ResponseEntity<>(newPayment, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/updateThePayment/{id}")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable("id") Long id, @RequestBody Payment updatedPayment) throws ResourceNotFoundException {
        Payment payment = paymentService.getPaymentById(id);
        if(payment!=null){
            Payment newPayment = paymentService.updatePaymentStatusById(id,updatedPayment);
            return new ResponseEntity<>(newPayment, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentById(@PathVariable Long id) throws ResourceNotFoundException {
        paymentService.deletePaymentById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Payment deleted successfully");
    }
}


