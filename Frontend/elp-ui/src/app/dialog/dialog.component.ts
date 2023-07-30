import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { trigger, state, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css'],
  animations: [
    trigger('messageAnimation', [
      state('Success', style({ color: 'green' })),
      state('Error', style({ color: 'red' })),
      state('Validation Error', style({ color: 'orange' })),
      transition('* => *', animate('300ms ease-in-out'))
    ])
  ]
})
export class DialogComponent {
  title: string;
  message: string;
  messageState: string; // Add this line

  constructor(@Inject(MAT_DIALOG_DATA) public data: { title: string, message: string }) {
    this.title = data.title;
    this.message = data.message;
    this.messageState = data.title; // Set the initial state based on the title
  }
}
