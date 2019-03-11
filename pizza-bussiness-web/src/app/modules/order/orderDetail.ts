/*
* Define the details of the chosen order
*/

export class OrderDetail {
  order_id: string;
  receive_name: string;
  receive_phone: string;
  receive_address: string;
  menu: OrderMenu[];
  total_amount: number;
  buy_phone: string;
  commit_time: string;
  shop_id: string;
  driver_id: string;
  start_deliver_time: string;
  arrive_time: string;
  state: string;
}

class OrderMenu {
  menu_name: string;
  menu_price: number;
  menu_count: number;
}
