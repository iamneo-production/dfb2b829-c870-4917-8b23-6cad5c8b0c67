import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './user';
import { url } from './config/url';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = url;

  constructor(private http: HttpClient) { }

  public getUserById(id: number) {
    return this.http.get<User>(`${this.baseUrl}users/${id}`);
  }

  public updateUser(id: number, user: User) {
    return this.http.put(`${this.baseUrl}users/${id}`, user);
  }

  public deleteUser(id: number) {
    return this.http.delete(`${this.baseUrl}users/${id}`);
  }
}
