import { CommandSchema, CommandName } from './schema';

export class Command implements CommandSchema {
  public name: CommandName = null;
  public selector: string = null;
  public value: string = null;
  public time: number = null;

  constructor(props: CommandSchema) {
    this.name = props.name;
    this.selector = props.selector;
    this.value = props.value;
    this.time = props.time;
  }
}
