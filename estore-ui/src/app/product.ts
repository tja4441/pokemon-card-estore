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

    public typesToJSONString(): string {
        let typesString: string = '['
        this.types.forEach(val => {
            if(val) typesString += '"' + val + '",'
        });
        if(typesString.length == 1) {
            return typesString + ']'
        }
        return typesString.slice(0, -1) + ']'
    }
}