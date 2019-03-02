import { PizzaSchema, PizzaWeakSchema } from './schema';

export class Pizza implements PizzaSchema {
  public id: number = 0;
  public name: string = '';
  public description: string = '';
  public price: number = 0;
  public tag: string = '';
  public img: string = '';
  public state: number = 0;

  constructor(pizza?: PizzaWeakSchema) {
    if (pizza) {
      this.id = pizza.id || this.id;
      this.name = pizza.name || this.name;
      this.description = pizza.description || this.description;
      this.price = pizza.price || this.price;
      this.tag = pizza.tag || this.tag;
      this.img = pizza.img || this.img;
      this.state = pizza.state || this.state;
    }
  }
  update(pizza: PizzaWeakSchema, onlyModified?: boolean) {
    if (onlyModified) {
      this.id = pizza.id || this.id;
      this.name = pizza.name || this.name;
      this.description = pizza.description || this.description;
      this.price = pizza.price || this.price;
      this.tag = pizza.tag || this.tag;
      this.img = pizza.img || this.img;
      this.state = pizza.state || this.state;
      return this;
    } else {
      const newPizza = Pizza.fromJS();
      newPizza.id = pizza.id || this.id;
      newPizza.name = pizza.name || this.name;
      newPizza.description = pizza.description || this.description;
      newPizza.price = pizza.price || this.price;
      newPizza.tag = pizza.tag || this.tag;
      newPizza.img = pizza.img || this.img;
      newPizza.state = pizza.state || this.state;
      return newPizza;
    }
  }

  static fromJS(pizza?: PizzaWeakSchema) {
    return new Pizza(pizza);
  }
}
