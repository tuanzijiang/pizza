import {Ingredient} from "./ingredient";

export class Factory {
  id: string;
  name: string;
  image: any;
  address: string;
  area: string;
  openHours: string;
  phone: string;
  maxNum: number;
  lon: string;
  lat: string;
  ingredientList: Ingredient[];
}
