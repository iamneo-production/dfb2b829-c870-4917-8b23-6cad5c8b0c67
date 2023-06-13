import { Component } from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent  {

  credentials= {
    fullname:'',
    emailid:'',
    message:''
  }
  
  onSubmit()
  {
    console.log("form is submitted");
  }
}
