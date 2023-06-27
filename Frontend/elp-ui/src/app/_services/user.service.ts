import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private PATH_OF_API = 'http://localhost:8080';
  private requestHeader = new HttpHeaders({ 'No-Auth': 'True' });
  message!: string; // Use definite assignment assertion modifier

  constructor(
    private httpClient: HttpClient,
    private userAuthService: UserAuthService
  ) {}

  public login(loginData: any) {
    return this.httpClient.post(`${this.PATH_OF_API}/authenticate`, loginData, {
      headers: this.requestHeader,
    });
  }

  public forUser() {
    return this.httpClient.get(`${this.PATH_OF_API}/forUser`, {
      responseType: 'text',
    });
  }

  public forAdmin() {
    return this.httpClient.get(`${this.PATH_OF_API}/forAdmin`, {
      responseType: 'text',
    });
  }

  public roleMatch(allowedRoles: string[]): boolean {
    const userRoles: string[] = this.userAuthService.getRoles();

    if (userRoles) {
      for (let i = 0; i < userRoles.length; i++) {
        for (let j = 0; j < allowedRoles.length; j++) {
          if (userRoles[i] === allowedRoles[j]) {
            return true;
          }
        }
      }
    }

    return false;
  }
}
