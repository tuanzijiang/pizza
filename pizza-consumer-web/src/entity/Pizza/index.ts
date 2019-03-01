import { PizzaSchema } from './schema';

export class Pizza implements PizzaSchema {
  public id: number = 0;
  public name: string = '';
  public description: string = '';
  public price: number = 0;
  public tag: string = '';
  public img: string = '';
  public state: number = 0;

  constructor(pizza?: {
    [prop: string]: any,
  }) {
    if (pizza) {
      this.id = pizza.id && this.id;
      this.name = pizza.name && this.name;
      this.description = pizza.description && this.description;
      this.price = pizza.price && this.price;
      this.tag = pizza.tag && this.tag;
      this.img = pizza.img && this.img;
      this.state = pizza.state && this.state;
    }
  }

  static fromJS(user?: {
    [prop: string]: any,
  }) {
    return new Pizza(user);
  }
}
