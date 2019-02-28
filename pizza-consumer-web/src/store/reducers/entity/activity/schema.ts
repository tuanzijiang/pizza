export enum ActivityStateEntity {
  INVALID = 0,
  START = 1,
  TO_START = 2,
  STOP = 3,
}

export interface ActivityEntity {
  activity_id: string;
  activity_name: string;
  creator_name: string;
  time: {
    start: string;
    end: string;
  };
  state: ActivityStateEntity;
  pages: PageEntity[];
}

export interface PageEntity {
  page_name: string;
  page_id: string;
  creator_name: string;
  time: {
    start: string;
    end: string;
  };
  state: string;
}

export interface ActivitiesEntity {
  activities: ActivityEntity[];
}
