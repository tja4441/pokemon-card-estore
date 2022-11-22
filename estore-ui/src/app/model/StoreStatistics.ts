import { ShoppingCart } from "./ShoppingCart";

export interface StoreStatistics {
    purchaseCount: number;
    averagePurchaseAmt: number;
    mostPopularProducts: number[];
    mostExpensiveCarts: ShoppingCart[];
    mostPopType: String;
    typeRevenue: Map<String, number>;
    avgSessionTime: number;
}