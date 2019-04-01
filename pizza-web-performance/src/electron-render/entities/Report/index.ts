import { ReportSchema } from './schema';
import { Process } from '../Process';

export class Report implements ReportSchema {
  public id: string = '';
  public processes: Process[] = [];

  constructor(props: ReportSchema) {
    this.id = props.id;
    this.processes = props.processes;
  }
}
