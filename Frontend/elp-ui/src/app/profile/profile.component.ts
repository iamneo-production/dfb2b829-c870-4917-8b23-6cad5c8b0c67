import { Component, ViewChild, TemplateRef } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { User } from '../user';
import { UserService } from '../user.service';
import { UserAuthService } from '../_services/user-auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  @ViewChild('editDialog') editDialog!: TemplateRef<any>;

  user: User = new User();
  userdetails: User[] = [];
  userDetails: any;
  userId: any;

  constructor(
    private userService: UserService,
    private dialog: MatDialog,
    public userAuthService: UserAuthService
  ) {
    this.userDetails = this.userAuthService.getUserdetails();
    this.userId = this.userDetails.id;
  }

  ngOnInit() {
    this.getUserdetails();
  }

  getUserdetails() {
    const id = this.userId; // Specify the ID of the user you want to fetch
    this.userService.getUserById(id).subscribe(
      (resp) => {
        console.log(resp);
        this.userdetails = [resp];
      },
      (err) => {
        console.log(err);
      }
    );
  }

  updateUser() {
    const id = this.userId;
    this.userService.updateUser(id, this.user).subscribe(
      (resp) => {
        console.log(resp);
        this.closeEditDialog(); // Close the edit dialog after successful update
      },
      (err) => {
        console.log(err);
      }
    );
  }

  openEditDialog(user: User) {
    this.user = { ...user }; // Copy the selected user details to the 'user' object for editing

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '300px'; // Adjust the width as per your requirements
    dialogConfig.height = '800px'; // Adjust the height as per your requirements
    dialogConfig.data = this.user;

    const dialogRef = this.dialog.open(this.editDialog, dialogConfig);

    dialogRef.afterClosed().subscribe(() => {
      // Handle dialog close event if needed
    });
  }

  closeEditDialog() {
    this.dialog.closeAll();
    this.user = new User();
  }
}
