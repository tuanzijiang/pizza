import {Ingredient} from "./ingredient";

export class Menu {
  id: string;
  name: string;
  type: string;
  image: any;
  price: number;
  tagName: string;
  description: string;
  state: string;
  ingredients: Ingredient[];
}
