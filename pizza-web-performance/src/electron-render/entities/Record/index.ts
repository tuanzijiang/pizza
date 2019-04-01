import { Command } from '../Command';
import { Response } from '../Response';
import { RecordSchema } from './schema';
import { Report } from '../Report';

export class Record implements RecordSchema {
  public id: string = null;
  public url: string = null;
  public name: string = null;
  public commands: Command[] = [];
  public successResp: Response[] = [];
  public errorResp: Response[] = [];
  public report: Report = null;

  constructor(props: RecordSchema) {
    this.id = props.id;
    this.url = props.url;
    this.name = props.name;
    this.commands = props.commands.map(v => new Command(v));
    this.successResp = props.successResp.map(v => new Response(v));
    this.errorResp = props.errorResp.map(v => new Response(v));
  }
}
