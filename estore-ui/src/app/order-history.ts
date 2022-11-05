import { Product } from "./product";

export interface OrderHistory {
    userID: number,
    cart: Product[],
    orderNumber: number,
    timestamp: string
}