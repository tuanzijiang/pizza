export interface UserSchema {
  id: number;
  name: string;
  phone: string;
  email: string;
  birthday: number;
  city: string;
  img: string;
  address: number;
  addresses: number[];
}

export interface UserWeakSchema {
  id?: number;
  name?: string;
  phone?: string;
  email?: string;
  birthday?: number;
  city?: string;
  img?: string;
  address?: number;
  addresses?: number[];
}
