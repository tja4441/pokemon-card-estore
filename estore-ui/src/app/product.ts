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
    TRAINER = "TRAINER",
    NOTATYPE = "NOTATYPE"
}

export class CardTypeConverter {
    public static stringToType(s: string): CardType {
        if(s === CardType.DARK) {
            return CardType.DARK
        }
        else if (s === CardType.DRAGON) {
            return CardType.DRAGON
        }
        else if (s === CardType.ELECTRIC) {
            return CardType.ELECTRIC
        }
        else if (s === CardType.FAIRY) {
            return CardType.FAIRY
        }
        else if (s === CardType.FIGHTING) {
            return CardType.FIGHTING
        }
        else if (s === CardType.FIRE) {
            return CardType.FIRE
        }
        else if (s === CardType.GRASS) {
            return CardType.GRASS
        }
        else if (s === CardType.NORMAL) {
            return CardType.NORMAL
        }
        else if (s === CardType.PSYCHIC) {
            return CardType.PSYCHIC
        }
        else if (s === CardType.STEEL) {
            return CardType.STEEL
        }
        else if (s === CardType.WATER) {
            return CardType.WATER
        }
        else if (s === CardType.TRAINER) {
            return CardType.TRAINER
        }
        return CardType.NOTATYPE
    }
}

export interface Product {
    id: number;
    name: string;
    types: CardType[];
    quantity: number;
    price: number;
}