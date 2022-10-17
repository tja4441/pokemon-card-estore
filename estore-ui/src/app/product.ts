import { Injectable } from "@angular/core";

Injectable({
    providedIn: 'root'
})
export interface Product {
    id: number;
    name: string;
    quantity: number;
    price: number;
}