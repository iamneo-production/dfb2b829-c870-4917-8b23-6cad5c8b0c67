import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from './payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }
  public getPaymentById(id: number) {
    return this.http.get<Payment>(`http://localhost:8080/payment/${id}`);
  }

  public updatePayment(id: number, payment: Payment){
    return this.http.put<Payment>(`http://localhost:8080/payment/updateById/${id}`, payment);
  }
}
