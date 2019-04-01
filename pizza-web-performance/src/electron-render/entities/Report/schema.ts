import { Process } from '../Process';

export interface ReportSchema {
  id: string;
  processes: Process[];
}
