import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  @ViewChild('passwordInput') passwordInput: any;
  @ViewChild('f', { static: false }) signInForm!: NgForm;

  id1: bigint=BigInt('');
  fname: string='';
  lname:string='';
  mail: string='';
  password: string='';
  addr:string='';
  phoneno:any=''
  


  formdata={
    id1: BigInt(''),
    fname: '',
    lname:'',
    mail: '',
    password:'',
    addr:'',
    phoneno:''
    
  }

  get id1Field() {
    return this.signInForm.form.get('id1');
  }

  get fnameField() {
    return this.signInForm.form.get('fname');
  }

  get lnameField() {
    return this.signInForm.form.get('lname');
  }

  get mailField() {
    return this.signInForm.form.get('mail');
  }

  get passwordField() {
    return this.signInForm.form.get('password');
  }

  get addrField() {
    return this.signInForm.form.get('addr');
  }

  get pnoField() {
    return this.signInForm.form.get('phoneno');
  }

  submitForm(f: NgForm) {
    if (f.invalid) {
      f.control.markAllAsTouched();
      return;
    }

    
  }


}
