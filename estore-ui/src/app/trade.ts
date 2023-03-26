import { Product } from "./product";

export interface Trade {
    userName: string;
    otherName: string;
    offer: Product;
    request: Product;
}