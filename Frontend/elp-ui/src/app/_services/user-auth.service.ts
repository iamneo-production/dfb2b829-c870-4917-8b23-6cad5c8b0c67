import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  constructor() {}

  public setRoles(roles: string[]) {
    localStorage.setItem('roles', JSON.stringify(roles));
  }

  public getRoles(): string[] {
    return JSON.parse(localStorage.getItem('roles') || '[]');
  }
  public setUserdetails(userdetails: any) {
    console.log('userdetails');
    console.log(userdetails);
    //print the userdetails type
    console.log(typeof userdetails);
    
    localStorage.setItem('userdetails', JSON.stringify(userdetails));
  }
  public getUserdetails(): any{
    return JSON.parse(localStorage.getItem('userdetails') || '{}');
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
  }

  public getToken(): string {
    return localStorage.getItem('jwtToken') || '';
  }

  public clear() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    return this.getRoles().length > 0 && this.getToken() !== '';
  }
}
