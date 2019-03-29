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
  BIND_REGISTER = 3,
}

export enum OrderStatus {
  UNKONWN = 0,
  CART = 1,
  UNPAID = 2,
  PAID = 3,
  CANCEL_CHECKING = 4,
  CANCELED = 5,
  CANCEL_FAILED = 6,
  DELIVERING = 7,
  RECEIVED = 8,
  FINISH = 9,
  WAIT_DELIVERY = 10,
  RECEIVE_FAIL = 11,
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
