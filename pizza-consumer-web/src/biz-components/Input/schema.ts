export enum InputType {
  STRING = 'STRING',
  NUMBER = 'NUMBER',
  SELECT = 'SELECT',
  COLOR = 'COLOR',
}

export interface InputInfo {
  [key: string]: InputConfig;
}

export interface InputConfig {
  name: string;
  inputType: InputTypeConfig;
}

export interface InputTypeConfig {
  type: InputType;
  options?: string[];
}

export interface InputProps {
  value: string;
}
