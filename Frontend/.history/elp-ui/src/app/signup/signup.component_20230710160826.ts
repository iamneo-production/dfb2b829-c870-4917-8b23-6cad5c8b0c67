import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import {HttpClient} from  '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  
  constructor(private http: HttpClient){}

  id1: string='';
    fname: string='';
    lname:string='';
    mail: string='';
    password: string='';
    addr:string='';
    phoneno:string=''
    

  register(f:NgForm):void
  {
  
    if (f.invalid) {
      f.control.markAllAsTouched();
      return;
    }

    let bodyData = {
      "id" : this.id1,
      "firstName" : this.fname,
      "lastName" : this.lname,
      "email" : this.mail,
      "password" : this.password,
      "address" : this.addr,
      "phoneNumber" : this.phoneno
    };
 
    this.http.post("http://localhost:8080/users/",bodyData).subscribe((resultData: any)=>
    {
        console.log(resultData);
        alert("User Registered Successfully")
        
        this.id1 = '';
        this.fname = '';
       this.lname = '';
       this.mail = '';
       this.password = '';
       this.addr= '';
       this.phoneno = '';
    });
  }




  

  
}
