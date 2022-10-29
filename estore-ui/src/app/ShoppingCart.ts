import { Product } from "./product";

export interface ShoppingCart {
    id: number;
    contents: Array<Product>;
    totalPrice: Number;
}