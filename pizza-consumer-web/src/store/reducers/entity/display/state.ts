import { DisplayInfoEntity } from './schema';

export class Display implements DisplayInfoEntity {
  public display = {};
  public display_order: string[] = [];
  public curr_display_id: string = '';
}
