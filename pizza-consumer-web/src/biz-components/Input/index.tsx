import * as React from 'react';
import { InputConfig } from './schema';
import { getInputByConfigType } from './config';

export interface InputState { }

export interface InputProps {
  value: any;
  config: InputConfig;
  rootKey: string;
  currKey: string;
  propName: string;
  propValue: any;
}

export default class Input extends React.PureComponent<InputProps, InputState> {
  render() {
    const { value, config, rootKey, currKey, propName, propValue } = this.props;
    const { inputType } = config;
    const { type } = inputType;
    const InputComponent = getInputByConfigType(type);
    const renderInput = InputComponent ?
      <InputComponent
        value={value}
        rootKey={rootKey}
        currKey={currKey}
        propName={propName}
        propValue={propValue}
      /> :
      <div>{value}</div>;
    return (
      <>
        {renderInput}
      </>
    );
  }
}
