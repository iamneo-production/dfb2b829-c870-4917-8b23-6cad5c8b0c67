import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from './payment';
import { url } from './config/url';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl: string = url;

  constructor(private http: HttpClient) { }

  public getPaymentById(id: number) {
    return this.http.get<Payment>(`${this.baseUrl}payment/${id}`);
  }

  public updatePayment(id: number, payment: Payment) {
    return this.http.put<Payment>(`${this.baseUrl}payment/updateById/${id}`, payment);
  }
}
