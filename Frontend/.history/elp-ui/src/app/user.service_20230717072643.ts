import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUserById(id: number) {
    return this.http.get<User>(`http://localhost:8080/users/${id}`);
  }

  public updateUser(id: number, user: User) {
    return this.http.put(`http://localhost:8080/users/${id}`, user);
  }

  public deleteUser(id: number) {
    return this.http.delete(`http://localhost:8080/users/${id}`);
  }
}
