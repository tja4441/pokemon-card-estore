import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class MessageService {
  //Array of strings log of msgs
  messages: string[] = [];

  /**
   * adds a msg to the msg log
   * @param message msg to add to log
   */
  add(message: string) {
    this.messages.push(message);
  }

  /**
   * clears all msgs from the log
   */
  clear() {
    this.messages = [];
  }
}