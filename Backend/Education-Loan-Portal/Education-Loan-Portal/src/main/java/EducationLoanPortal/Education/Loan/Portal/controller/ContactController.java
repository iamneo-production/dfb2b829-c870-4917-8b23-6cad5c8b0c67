package EducationLoanPortal.Education.Loan.Portal.controler;


import EducationLoanPortal.Education.Loan.Portal.model.Contact;
import EducationLoanPortal.Education.Loan.Portal.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("contact")
public class ContactController {


    @Autowired
    private ContactService contactService;

    @PostMapping(path = "/save")
    public String saveContact(@RequestBody Contact contact)
    {
       Contact details= contactService.addContact(contact);

        return String.valueOf(new ResponseEntity<>(details, HttpStatus.CREATED));
    }


    @GetMapping(path = "getAllContact")
    public List<Contact> getAllContact() {
        List<Contact> allContact = contactService.getAllContact();
        return allContact;
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteContact(@PathVariable(value = "id") int id) {
        boolean deleteContact = contactService.deleteContact(id);
        return "delete";
    }


}
