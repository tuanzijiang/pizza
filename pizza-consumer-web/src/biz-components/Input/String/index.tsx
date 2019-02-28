import * as React from 'react';
import { InputProps } from '..';
import autobind from 'autobind-decorator';
import { updateDisplayByKey } from '@src/utils/store';

export interface InputStringState { }

export interface InputStringProps extends InputProps { }

export default class InputString extends React.PureComponent<InputStringProps, InputStringState> {
  @autobind
  handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    const { currKey, rootKey, propName } = this.props;
    updateDisplayByKey(propName, e.target.value, rootKey, currKey);
  }

  render() {
    const { value } = this.props;
    return (
      <input
        type="string"
        value={value}
        onChange={this.handleChange}
      />
    );
  }
}
