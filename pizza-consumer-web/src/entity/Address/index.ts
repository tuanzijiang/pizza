import { AddressSchema, SexSchema, AddressWeakSchema } from './schema';

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
      this.sex = address.sex || this.sex;
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
      this.sex = address.sex || this.sex;
      this.address = address.address || this.address;
      this.addressDetail = address.addressDetail || this.addressDetail;
      this.phone = address.phone || this.phone;
      this.tag = address.tag || this.tag;
      return this;
    } else {
      const newOrder = Address.fromJS();
      newOrder.id = address.id || this.id;
      newOrder.name = address.name || this.name;
      newOrder.sex = address.sex || this.sex;
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
}
