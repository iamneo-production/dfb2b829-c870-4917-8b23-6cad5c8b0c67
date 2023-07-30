import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';
import { url } from '../config/url'; 

@Injectable({
  providedIn: 'root',
})
export class UserService {
  PATH_OF_API = url;

  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });
  constructor(
    private httpclient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  public login(loginData: any) {
    return this.httpclient.post(this.PATH_OF_API + 'authenticate', loginData, {
      headers: this.requestHeader,
    });
  }

  public forUser() {
    return this.httpclient.get(this.PATH_OF_API + 'forUser', {
      responseType: 'text',
    });
  }

  public forAdmin() {
    return this.httpclient.get(this.PATH_OF_API + 'forAdmin', {
      responseType: 'text',
    });
  }

  public checktherole(s: string) {
    const userRoles: string[] = this.userAuthService.getRoles();
    console.log('userRoles');
    console.log(userRoles);
    console.log('s');
    console.log(s);

    return false;
  }

  public roleMatch(allowedRoles: string[]): boolean {
    let isMatch = false;
    const userRoles: any = this.userAuthService.getRoles();


    if (userRoles != null && userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i].roleName === allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          } 
          
        }
      }
    }
    return isMatch;
  }
}
