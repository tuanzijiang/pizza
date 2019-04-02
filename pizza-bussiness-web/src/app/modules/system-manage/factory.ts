import {Ingredient} from "./ingredient";

export class Factory {
  id: string;
  name: string;
  image: any;
  address: string;
  openHours: string;
  phone: string;
  maxNum: number;
  lon: string;
  lat: string;
  ingredientList: Ingredient[];
}
