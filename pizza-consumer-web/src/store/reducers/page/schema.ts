import { ManagePageState } from './manage/schema';
export { ManagePageState } from './manage/schema';

import { EditPageState } from './edit/schema';
export { EditPageState } from './edit/schema';

export interface PageState {
  manage: ManagePageState;
  edit: EditPageState;
}
