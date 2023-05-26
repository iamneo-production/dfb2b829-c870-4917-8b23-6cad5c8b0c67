import { Component } from '@angular/core';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent {
  formdata={uname:"",pwd:""};
  submit=false;
  loading=false;
  onSubmit(){
    this.loading=true;
    console.log("logged in");
  }

}
