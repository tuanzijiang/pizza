import * as React from 'react';
import { gray_4 } from '@ui/base';
import './index.scss';

export interface ISpinnerProps {
  style?: React.CSSProperties;
  color?: string;
}

export default class Spinner extends React.Component<ISpinnerProps, {}> {
  public static defaultProps = {
    style: {
      height: 16,
      width: 16,
    },
    color: gray_4,
  };

  public render() {
    const { style, color } = this.props;
    return (
      <svg
        className="ui-spinner"
        style={style}
        viewBox="0 0 66 66"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle
          className="ui-spinner-path"
          fill="none"
          style={{ stroke: color }}
          strokeWidth="6"
          strokeLinecap="round"
          cx="33"
          cy="33"
          r="30"
        />
      </svg>
    );
  }
}
