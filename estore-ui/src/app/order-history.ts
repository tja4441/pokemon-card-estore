import { ShoppingCart } from "./ShoppingCart";

export interface OrderHistory {
    userID: number,
    cart: ShoppingCart,
    orderNumber: number,
    timestamp: string
}