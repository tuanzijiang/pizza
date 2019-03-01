import { AddressSchema, SexSchema } from './schema';

export class Address implements AddressSchema {
  public id: number = 0;
  public name: string = '';
  public sex: SexSchema = SexSchema.FEMALE;
  public address: string = '';
  public addressDetail: string = '';
  public phone: string = '';
  public tag: string = '';

  constructor(address?: {
    [prop: string]: any,
  }) {
    if (address) {
      this.id = address.id && this.id;
      this.name = address.name && this.name;
      this.sex = address.sex && this.sex;
      this.address = address.address && this.address;
      this.addressDetail = address.addressDetail && this.addressDetail;
      this.phone = address.phone && this.phone;
      this.tag = address.tag && this.tag;
    }
  }

  static fromJS(address?: {
    [prop: string]: any,
  }) {
    return new Address(address);
  }
}
