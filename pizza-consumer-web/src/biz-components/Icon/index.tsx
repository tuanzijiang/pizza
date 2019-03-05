import * as React from 'react';
import cx from 'classnames';
import './index.scss';
import './icon/iconfont.css';

export interface IconProps {
  name: string;
  classnames?: string[] | string;
  onClick?: () => void;
}

export interface IconState { }

export default class Icon extends React.PureComponent<IconProps, IconState> {
  render() {
    const { name, classnames, onClick } = this.props;
    const extra = [].concat(classnames);
    const iconName = cx(
      'iconfont',
      `pizza-${name}`,
      ...extra,
    );
    return (
      <div
        className="icon-main"
        onClick={onClick}
      >
        <span className={iconName} />
      </div>
    );
  }
}
