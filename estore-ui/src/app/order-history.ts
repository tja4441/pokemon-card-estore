import { Product } from "./product";

export interface orderHistory {
    userID: number,
    cart: Product[],
    orderNumber: number,
    timestamp: string
}