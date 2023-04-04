import { Product } from "./product";

export interface Trade {
    fromUser: string;
    toUser: string;
    offer: Product;
    request: Product;
}