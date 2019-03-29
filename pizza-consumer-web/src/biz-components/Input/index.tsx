import * as React from 'react';
import './index.scss';

export interface InputProps {
  left?: React.ReactNode;
  right?: React.ReactNode;
  middle?: React.ReactNode;
  defaultValue?: string;
  placeholde?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  type?: string;
  value?: string | number;
}

export interface InputState { }

export default class Ipnut extends React.PureComponent<InputProps, InputState> {
  static defaultProps = {
    type: 'string',
  };

  render() {
    const { left, right, placeholde, type, value, onChange, middle } = this.props;
    return (
      <div className="input-wrapper">
        <div className="input-left">
          {left}
        </div>
        <div className="input-content">
          {middle ? middle :
            <input placeholder={placeholde} type={type} value={value} onChange={onChange} />
          }
        </div>
        <div className="input-right">
          {right}
        </div>
      </div>
    );
  }
}
