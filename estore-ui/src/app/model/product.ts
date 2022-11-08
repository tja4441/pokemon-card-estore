export enum CardType {
    DARK = "DARK",
    DRAGON = "DRAGON",
    ELECTRIC = "ELECTRIC",
    FAIRY = "FAIRY",
    FIGHTING = "FIGHTING",
    FIRE = "FIRE",
    GRASS = "GRASS",
    NORMAL = "NORMAL",
    PSYCHIC = "PSYCHIC",
    STEEL = "STEEL",
    WATER = "WATER",
    TRAINER = "TRAINER"
}

export class Product {
    id: number;
    name: string;
    types: string[];
    quantity: number;
    price: number;

    constructor(id: number, name: string, types: string[], quantity: number, price: number) {
        this.id = id
        this.name = name
        this.types = types
        this.quantity = quantity
        this.price = price
    }
}