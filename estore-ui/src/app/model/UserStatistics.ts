import { Product } from "./product";
import { ShoppingCart } from "./ShoppingCart";

export interface UserStatistics {
    username: string;
    id: number;
    purchaseCounter: number;
    lifetimeSpending: number;
    lifetimeSessionTime: number;
    averagePurchaseAmt: number;
    purchasedCounts: Map<String, number>;
    typeCounts: Map<String, number>;
    typeRevenues: Map<String, number>;
    averageSessionTime: number;
    mostExpensiveOrder: ShoppingCart;
    mostPurchased: Product;
    mostPopularType: String;
}