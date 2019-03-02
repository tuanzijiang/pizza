import { UserSchema } from './schema';

export class User implements UserSchema {
  public id: number = 0;
  public name: string = '';
  public phone: string = '';
  public email: string = '';
  public birthday: number = 0;
  public city: string = '';
  public img: string = '';

  constructor(user?: {
    [prop: string]: any,
  }) {
    if (user) {
      this.id = user.id || this.id;
      this.name = user.name || this.name;
      this.phone = user.phone || this.phone;
      this.email = user.email || this.email;
      this.birthday = user.birthday || this.birthday;
      this.city = user.city || this.city;
      this.img = user.img || this.img;
    }
  }

  static fromJS(user?: {
    [prop: string]: any,
  }) {
    return new User(user);
  }
}
