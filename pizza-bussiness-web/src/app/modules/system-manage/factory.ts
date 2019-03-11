import {Ingredient} from "./ingredient";

export class Factory {
  fac_id: string;
  name: string;
  pic: any;
  address: string;
  area: string;
  open_hours: string;
  phone: string;
  max_orders: number;
  longitude: string;
  latitude: string;
  ingridients: Ingredient[];
}
