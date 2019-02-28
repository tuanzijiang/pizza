import { ActivityEntity , PageEntity, ActivityStateEntity } from './schema';

class Time {
  public start = '';
  public end = '';
}

export class Page implements PageEntity {
  public page_name = '';
  public page_id = '';
  public creator_name = '';
  public time = new Time();
  public state = '';
}

export class Activity implements ActivityEntity {
  public activity_id = '';
  public activity_name = '';
  public creator_name = '';
  public time = new Time();
  public state = ActivityStateEntity.INVALID;
  public pages: Page[] = [];
}
