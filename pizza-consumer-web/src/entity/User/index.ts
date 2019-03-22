import { UserSchema, UserWeakSchema } from './schema';

export class User implements UserSchema {
  public id: number = 0;
  public name: string = '';
  public phone: string = '';
  public email: string = '';
  public birthday: number = 0;
  public city: string = '';
  public img: string = '';
  public address: number = 0;
  public addresses: number[] = [];

  constructor(user?: UserWeakSchema) {
    if (user) {
      this.id = user.id || this.id;
      this.name = user.name || this.name;
      this.phone = user.phone || this.phone;
      this.email = user.email || this.email;
      this.birthday = user.birthday || this.birthday;
      this.city = user.city || this.city;
      this.img = user.img || this.img;
      this.address = user.address || this.address;
      this.addresses = user.addresses || this.addresses;
    }
  }

  update(user: UserWeakSchema, onlyModified?: boolean) {
    if (onlyModified) {
      this.id = user.id || this.id;
      this.name = user.name || this.name;
      this.phone = user.phone || this.phone;
      this.email = user.email || this.email;
      this.birthday = user.birthday || this.birthday;
      this.city = user.city || this.city;
      this.img = user.img || this.img;
      this.address = user.address || this.address;
      this.addresses = user.addresses || this.addresses;
      return this;
    } else {
      const newUser = User.fromJS();
      newUser.id = user.id || this.id;
      newUser.name = user.name || this.name;
      newUser.phone = user.phone || this.phone;
      newUser.email = user.email || this.email;
      newUser.birthday = user.birthday || this.birthday;
      newUser.city = user.city || this.city;
      newUser.img = user.img || this.img;
      newUser.address = user.address || this.address;
      newUser.addresses = user.addresses || this.addresses;
      return newUser;
    }
  }

  static fromJS(user?: UserWeakSchema) {
    return new User(user);
  }
}
