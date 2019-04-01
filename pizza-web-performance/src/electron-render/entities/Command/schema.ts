export enum CommandName {
  CLICK = 'CLICK',
  CHANGE = 'CHANGE',
}

export interface CommandSchema {
  selector: string;
  name: CommandName;
  time: number; // 距离上个command触发的时间
  value?: string;
}
