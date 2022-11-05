import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {
  private historyURL = 'http://localhost:8080/history';   // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService) {}
}

  getAllOrders(): Observable