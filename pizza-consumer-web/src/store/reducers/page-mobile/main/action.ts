/**
 * action type
 */
export const UPDATE_NAV_IDX = Symbol('UPDATE_NAV_IDX');

export const UPDATE_ORDER_IDS = Symbol('UPDATE_ORDER_IDS');

/**
 * action creator
 */
const updateNavIdx = (navIdx: number) => ({
  navIdx,
  type: UPDATE_NAV_IDX,
});

const updateOrdersId = (orderIds: string[]) => ({
  orderIds,
  type: UPDATE_ORDER_IDS,
});

export default {
  updateNavIdx,
  updateOrdersId,
};
