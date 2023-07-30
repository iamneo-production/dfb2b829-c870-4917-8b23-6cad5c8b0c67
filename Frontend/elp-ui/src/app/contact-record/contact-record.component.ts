import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-contact-record',
  templateUrl: './contact-record.component.html',
  styleUrls: ['./contact-record.component.css']
})
export class ContactRecordComponent {

  ContactArray : any[] =[];

  name: string ="";
  email: string ="";
  message: string ="";

  currentStudentId ="";

  constructor(private http: HttpClient){
    this.getAllContact();
  }

  getAllContact(){

    this.http.get("http://localhost:8080/contact/getAllContact")

    .subscribe((resultData: any)=>
    {
      console.log(resultData);
      this.ContactArray = resultData;
    });
  }
  contact(){
    let bodyData = {
      "fullname" : this.name,
      "email" : this.email,
      "message" : this.message
    };

    
   this.http.post("http://localhost:8080/contact/save",bodyData).subscribe((resultData: any)=>
   {
        console.log(resultData);
        alert("Successfully submitted");
        
   
        this.name = '';
        this.email = '';
        this.message = ''
      });
     }
     save()
     {
       this.contact();
     }
     setDelete(data: any){
      this.http.delete("http://localhost:8080/contact/delete"+"/"+data.id,{responseType: 'text'}).subscribe((resultData: any)=>
      {
        console.log(resultData);
        alert("Record Delete")
        this.getAllContact();
        this.name ='';
        this.email='';
        this.message='';
      });
     }
  
}
