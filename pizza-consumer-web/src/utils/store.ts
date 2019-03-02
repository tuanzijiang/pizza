import _ from 'lodash';
// import { Tag } from '@biz-components/Tag/schema';
import { getRandomStr } from '@utils/random';
import { entity as entityActionCreator } from '@store/action';

const ID_LENGTH = 10;

// export const fetchState = () => __STORE__.getState();

// export const addDisplayInfo = (config: Tag) => {
//   let id;
//   let clone;
//   const state = fetchState();
//   const { tag_children } = config;
//   const { display, display_order } = state.entity.display;

//   const newDisplay = _.cloneDeep(display);
//   const newDisplayOrder = _.cloneDeep(display_order);

//   // 将根配置项加入页面
//   id = getRandomStr(ID_LENGTH);
//   while (newDisplayOrder.indexOf(id) !== -1) {
//     id = getRandomStr(ID_LENGTH);
//   }
//   clone = _.cloneDeep(config);
//   clone.id = id;
//   newDisplayOrder.push(id);
//   newDisplay[id] = clone;
//   const curr_display_id = id;

//   // 将子配置项加入页面
//   clone.tag_children = tag_children.map((child: Tag) => {
//     id = getRandomStr(ID_LENGTH);
//     while (newDisplayOrder.indexOf(id) !== -1) {
//       id = getRandomStr(ID_LENGTH);
//     }
//     clone = _.cloneDeep(child);
//     clone.id = id;
//     newDisplay[id] = clone;
//     return clone;
//   });

//   const action = entityActionCreator.display.addDisplayInfo({
//     curr_display_id,
//     display: newDisplay,
//     display_order: newDisplayOrder,
//   });
//   __STORE__.dispatch(action);
// };

// export const updateDisplayByKey = (
//   propName: string, propValue: any, rootKey: string, currKey: string,
// ) => {
//   const action = entityActionCreator.display.updateDisplayKey(
//     propName,
//     propValue,
//     rootKey,
//     currKey,
//   );
//   __STORE__.dispatch(action);
// };
