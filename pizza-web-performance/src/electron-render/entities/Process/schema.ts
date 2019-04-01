import { Command } from '../Command';
import { Record } from '../Record';
import { Response } from '../Response';

export interface ProcessSchema {
  id: string;
  record: Record;
  name: string;
  commands: Command[];
  successResp: Response[];
  errorResp: Response[];
}
