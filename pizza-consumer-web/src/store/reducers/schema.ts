import { PageState } from './page/schema';
export { PageState, ManagePageState } from './page/schema';

import { EntityState } from './entity/schema';
export {
  EntityState, ActivityEntity, PageEntity, ActivityStateEntity,
} from './entity/schema';

export interface StoreState {
  page: PageState;
  entity: EntityState;
}
