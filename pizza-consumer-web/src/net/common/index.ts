export enum ResultType {
  FAILURE = 0,
  SUCCESS = 1,
}

export enum LoginType {
  PASSWORD = 0,
  VERIFICATION = 1,
}

export enum Sex {
  MALE = 0,
  FEMALE = 1,
}

export enum VerificationType {
  LOGIN = 0,
  FINDPW = 1,
  REGISTER = 2,
}

export enum OrderStatus {
  CART = 0,
  UNPAID = 1,
  PAID = 2,
  CANCEL_CHECKING = 3,
  CANCELED = 4,
  CANCEL_FAILED = 5,
  DELIVERING = 6,
  RECEIVED = 7,
  FINISH = 8,
}

export enum PizzaStatus {
  SOLE_OUT = 0,
  IN_SALES = 1,
}

export interface Address {
  id: number;
  name: string;
  sex: Sex;
  address: string;
  addressDetail: string;
  phone: string;
  tag: string;
}

export interface User {
  id: number;
  name: string;
  phone: string;
  email: string;
  birthday: number;
  city: string;
  img: string;
  address: Address;
}

export interface Pizza {
  id: number;
  name: string;
  description: string;
  price: number;
  tag: string;
  img: string;
  status: PizzaStatus;
  count: number;
}

export interface Order {
  id: string;
  startTime: number;
  pizzas: Pizza[];
  address: Address;
  status: OrderStatus;
  phone: string;
}
