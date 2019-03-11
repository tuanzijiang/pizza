import {Timestamp} from "rxjs";

export class ChargebackPending {
  order_id: string;
  receiver: string;
  phone: string;
  commit_time: Date;
  cancel_time: Date;
  duration: string;
  status: string;
}
