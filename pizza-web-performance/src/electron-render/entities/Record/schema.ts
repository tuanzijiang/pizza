import { Command } from '../Command';
import { Response } from '../Response';
import { Report } from '@entities/Report';

export interface RecordSchema {
  id: string;
  url: string;
  name: string;
  commands: Command[];
  successResp: Response[];
  errorResp: Response[];
  report: Report;
}
