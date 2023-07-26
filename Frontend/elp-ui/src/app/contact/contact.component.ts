import { Component, TemplateRef } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { trigger, transition, style, animate } from '@angular/animations'; 

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.3s', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('0.3s', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class ContactComponent {

  name: string = "";
  email: string = "";
  message: string = "";

  constructor(private http: HttpClient, private dialog: MatDialog) { }

  contact() {
    let bodyData = {
      "fullname": this.name,
      "email": this.email,
      "message": this.message
    };

    this.http.post("http://localhost:8080/contact/save", bodyData).subscribe(
      (resultData: any) => {
        console.log(resultData);

        // Open the dialog component to show the success message
        this.openDialog('Success', 'Submitted Successfully');

        this.name = '';
        this.email = '';
        this.message = '';
      },
      (error: HttpErrorResponse) => {
        // Handle error, you can also open a dialog for showing error message if needed.
        console.log(error);
        this.openDialog('Error', 'Failed to submit the form');
      }
    );
  }

  openDialog(title: string, message: string) {
    this.dialog.open(DialogComponent, {
      width: '400px',
      data: { title, message },
    });
  }

  onSubmit() {
    console.log("form is submitted");
  }
}
