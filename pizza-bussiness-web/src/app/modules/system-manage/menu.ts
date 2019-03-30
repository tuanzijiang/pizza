import {Ingredient} from "./ingredient";

export class Menu {
  pizza_id: string;
  name: string;
  type: string;
  pic: any;
  price: number;
  desc: string;
  status: string;
  ingridients: Ingredient[];
}
