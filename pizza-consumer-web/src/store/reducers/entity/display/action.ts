import { DisplayInfoEntity, DisplayEntity } from './schema';

/**
 * action type
 */
export const ADD_DISPLAY = Symbol('ADD_DISPLAY');
export const UPDATE_DISPLAY = Symbol('UPDATE_DISPLAY');
export const UPDATE_DISPLAY_ID = Symbol('UPDATE_DISPLAY_ID');
export const UPDATE_DISPLAY_KEY = Symbol('UPDATE_DISPLAY_Key');

/**
 * action creator
 */
const addDisplayInfo = (displayInfo: DisplayInfoEntity) => ({
  displayInfo,
  type: ADD_DISPLAY,
});

const updateDisplayId = (displayId: string) => ({
  displayId,
  type: UPDATE_DISPLAY_ID,
});

const updateDisplay = (display: DisplayEntity) => ({
  display,
  type: UPDATE_DISPLAY_ID,
});

const updateDisplayKey = (propName: string, propValue: any, rootKey: string, currKey: string) => ({
  propName,
  propValue,
  rootKey,
  currKey,
  type: UPDATE_DISPLAY_KEY,
});

export default {
  addDisplayInfo,
  updateDisplay,
  updateDisplayId,
  updateDisplayKey,
};
