import * as React from 'react';
import cx from 'classnames';
import './index.scss';

export enum ToastKind {
  MOBILE = 0,
  PC = 1,
}

export interface ToastProps {
  text: string;
  kind: ToastKind;
  visibility: boolean;
}

export interface ToastState { }

export default class Toast extends React.PureComponent<ToastProps, ToastState> {
  static defaultProps = {
    text: '',
    kind: ToastKind.MOBILE,
    visibility: true,
  };

  render() {
    const { text, visibility, kind } = this.props;
    return (
      <div className={cx({
        'toast-wrapper': true,
        'toast-wrapper_mobile': kind !== ToastKind.PC,
        'toast-wrapper_pc': kind === ToastKind.PC,
        'toast-wrapper_visibility': visibility,
      })}>
        <span className="toast-text">{text}</span>
      </div>
    );
  }
}
