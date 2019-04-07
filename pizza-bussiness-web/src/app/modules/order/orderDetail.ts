/*
* Define the details of the chosen order
*/

export class OrderDetail {
  orderId: any;
  receiveName: string;
  receivePhone: string;
  receiveAddress: string;
  menuList: OrderMenu[];
  totalAmount: number;
  buyPhone: string;
  commitTime: string;
  shopId: string;
  driverId: string;
  startDeliverTime: string;
  arriveTime: string;
  state: string;
}

class OrderMenu {
  name: string;
  price: number;
  count: number;
}
