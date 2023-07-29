package EducationLoanPortal.Education.Loan.Portal.service;


import EducationLoanPortal.Education.Loan.Portal.model.Contact;
import EducationLoanPortal.Education.Loan.Portal.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final ContactRepo contactRepo;

    @Autowired
    public ContactService(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public String addContact(Contact contact) {





        contactRepo.save(contact);

        return contact.getFullname();
    }

    public List<Contact> getAllContact() {

        List<Contact> getContact = contactRepo.findAll();
        List<Contact> contactList = new ArrayList<>();
        for(Contact c: getContact)
        {
            Contact contact = new Contact(

                    c.getId(),
                    c.getFullname(),
                    c.getEmail(),
                    c.getMessage()
            );
            contactList.add(contact);

        }
        return contactList;
    }

    public boolean deleteContact(int id) {

        if(contactRepo.existsById(id)){

            contactRepo.deleteById(id);
        }
        else{
            System.out.println("Contact ID not found");
        }



        return true;
    }

}
