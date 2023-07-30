package EducationLoanPortal.Education.Loan.Portal.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;
    @Column(name="fullname")
    private String fullname;
    @Column(name="email")
    private String email;
    @Column(name="message")
    private String message;


    public Contact(int id, String fullname, String email, String message) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.message = message;
    }

    public Contact() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
