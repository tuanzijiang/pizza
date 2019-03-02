export interface PizzaSchema {
  id: number;
  name: string;
  description: string;
  price: number;
  tag: string;
  img: string;
  state: number;
}

export interface PizzaWeakSchema {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  tag?: string;
  img?: string;
  state?: number;
}

export interface PizzaMap {
  [prop: number]: PizzaSchema | PizzaWeakSchema;
}
