import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {

 name:String ="";
 email:String ="";
 message:String ="";
  
  
  
  constructor(private http: HttpClient)
  {

  }
  contact()
  {
    let bodyData = {
      "fullname" : this.name,
      "email" : this.email,
      "message" : this.message
    };

   this.http.post("http://localhost:8080/api/v1/contact/save",bodyData).subscribe((resultData: any)=>
{
     console.log(resultData);
     alert("Successfully submitted")

     this.name = '';
     this.email = '';
     this.message = ''
   });
  }

  save()
  {
    this.contact();
  }
  
 
  
  onSubmit()
  {
    console.log("form is submitted");
  }

}

