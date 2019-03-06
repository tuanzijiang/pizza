import { AddressSchema, SexSchema, AddressWeakSchema } from './schema';

const SEX_APPELLATION = {
  [SexSchema.FEMALE]: '女士',
  [SexSchema.MALE]: '先生',
};

export class Address implements AddressSchema {
  public id: number = 0;
  public name: string = '';
  public sex: SexSchema = SexSchema.FEMALE;
  public address: string = '';
  public addressDetail: string = '';
  public phone: string = '';
  public tag: string = '';

  constructor(address?: AddressWeakSchema) {
    if (address) {
      this.id = address.id || this.id;
      this.name = address.name || this.name;
      if (address.sex === 0 || address.sex === 1) {
        this.sex = address.sex;
      }
      this.address = address.address || this.address;
      this.addressDetail = address.addressDetail || this.addressDetail;
      this.phone = address.phone || this.phone;
      this.tag = address.tag || this.tag;
    }
  }

  update(address: AddressWeakSchema, onlyModified?: boolean) {
    if (onlyModified) {
      this.id = address.id || this.id;
      this.name = address.name || this.name;
      if (address.sex === 0 || address.sex === 1) {
        this.sex = address.sex;
      }
      this.address = address.address || this.address;
      this.addressDetail = address.addressDetail || this.addressDetail;
      this.phone = address.phone || this.phone;
      this.tag = address.tag || this.tag;
      return this;
    } else {
      const newOrder = Address.fromJS();
      newOrder.id = address.id || this.id;
      newOrder.name = address.name || this.name;
      if (address.sex === 0 || address.sex === 1) {
        newOrder.sex = address.sex;
      } else {
        newOrder.sex = this.sex;
      }
      newOrder.address = address.address || this.address;
      newOrder.addressDetail = address.addressDetail || this.addressDetail;
      newOrder.phone = address.phone || this.phone;
      newOrder.tag = address.tag || this.tag;
      return newOrder;
    }
  }

  static fromJS(address?: AddressWeakSchema): Address {
    return new Address(address);
  }

  static toAppellation(address: AddressSchema): string {
    const { sex, name } = address;
    return `${name.charAt(0)}${SEX_APPELLATION[sex]}`;
  }
}
