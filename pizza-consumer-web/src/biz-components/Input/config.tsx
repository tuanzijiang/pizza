import InputString from './String';
import { InputType } from './schema';

const InputConfigTypeMap: {
  [props: string]: any;
} = {
  [InputType.STRING]: InputString,
  [InputType.NUMBER]: InputString,
  [InputType.SELECT]: InputString,
  [InputType.COLOR]: InputString,
};

export const getInputByConfigType = (type: string) => {
  return InputConfigTypeMap[type];
};
