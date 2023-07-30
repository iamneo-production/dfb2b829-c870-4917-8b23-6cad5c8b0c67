import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StringEncryptionService {
  private readonly SHIFT_AMOUNT = 3;

  encodeString(input: string): string {
    let encryptedString = '';
    for (const c of input) {
      if (this.isLetterOrDigit(c)) {
        let shiftedChar = c.charCodeAt(0) + this.SHIFT_AMOUNT;
        if (
          (c.toUpperCase() === c && shiftedChar > 'Z'.charCodeAt(0)) ||
          (c.toLowerCase() === c && shiftedChar > 'z'.charCodeAt(0)) ||
          (!isNaN(Number(c)) && shiftedChar > '9'.charCodeAt(0))
        ) {
          shiftedChar -= 26;
        }
        encryptedString += String.fromCharCode(shiftedChar);
      } else {
        encryptedString += c;
      }
    }
    return encryptedString;
  }

  decodeString(input: string): string {
    let decryptedString = '';
    for (const c of input) {
      if (this.isLetterOrDigit(c)) {
        let shiftedChar = c.charCodeAt(0) - this.SHIFT_AMOUNT;
        if (
          (c.toUpperCase() === c && shiftedChar < 'A'.charCodeAt(0)) ||
          (c.toLowerCase() === c && shiftedChar < 'a'.charCodeAt(0)) ||
          (!isNaN(Number(c)) && shiftedChar < '0'.charCodeAt(0))
        ) {
          shiftedChar += 26;
        }
        decryptedString += String.fromCharCode(shiftedChar);
      } else {
        decryptedString += c;
      }
    }
    return decryptedString;
  }

  private isLetterOrDigit(c: string): boolean {
    return c.match(/[a-zA-Z0-9]/) !== null;
  }
}
