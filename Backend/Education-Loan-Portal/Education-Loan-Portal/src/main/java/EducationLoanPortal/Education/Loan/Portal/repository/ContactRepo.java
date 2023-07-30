package EducationLoanPortal.Education.Loan.Portal.repository;


import EducationLoanPortal.Education.Loan.Portal.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository

public interface ContactRepo extends JpaRepository<Contact,Integer> {



}
