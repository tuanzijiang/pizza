/**
 * action type
 */
export const UPDATE_NAV_IDX = Symbol('NAV_IDX');

/**
 * action creator
 */
const updateNavIdx = (navIdx: number) => ({
  navIdx,
  type: UPDATE_NAV_IDX,
});

export default {
  updateNavIdx,
};
