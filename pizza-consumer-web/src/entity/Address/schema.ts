export enum SexSchema {
  MALE = 0,
  FEMALE = 1,
}

export interface AddressSchema {
  id: number;
  name: string;
  sex: SexSchema;
  address: string;
  addressDetail: string;
  phone: string;
  tag: string;
}
