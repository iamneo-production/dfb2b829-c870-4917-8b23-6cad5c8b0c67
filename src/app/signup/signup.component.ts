import { Component } from '@angular/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  formdata = {id:"",fname:"",lname:"",email:"",password:"",addr:"",phone:""};
  submit=false;
  loading=false;
  onSubmit(){
    this.loading=true;
    console.log("form submitted");
  }
  

}
