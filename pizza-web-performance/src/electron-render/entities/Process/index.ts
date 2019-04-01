import { Command } from '../Command';
import { Response } from '../Response';
import { Record } from '../Record';
import { ProcessSchema } from './schema';

export class Process implements ProcessSchema {
  public id: string = null;
  public record: Record = null;
  public name: string = null;
  public commands: Command[] = [];
  public successResp: Response[] = [];
  public errorResp: Response[] = [];

  constructor(props: ProcessSchema) {
    this.id = props.id;
    this.record = props.record;
    this.name = props.name;
    this.commands = props.commands;
    this.successResp = props.successResp;
    this.errorResp = props.errorResp;
  }
}
