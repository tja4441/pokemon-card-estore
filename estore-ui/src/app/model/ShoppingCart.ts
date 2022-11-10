import { Product } from "./product";

export interface ShoppingCart {
    id: number;
    contents: Set<Product>;
    totalPrice: number;
}