import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Loan } from './loan';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private http: HttpClient) { }

  getLoansByStatus(status: string): Observable<any[]> {
    const url = `http://localhost:8080/loans?status=${status}`;
    return this.http.get<any[]>(url);
  }

  getLoansByUserId(userId: number): Observable<any[]> {
    const url = `http://localhost:8080/loans?user=${userId}`;
    return this.http.get<any[]>(url);
  }

  deleteLoan(id: number): Observable<void> {
    const url = `http://localhost:8080/loans/${id}`;
    return this.http.delete<void>(url);
  }

  updateLoan(id: number, loan: Loan): Observable<Loan> {
    const url = `http://localhost:8080/loans/${id}`;
    return this.http.put<Loan>(url, loan);
  }
}
