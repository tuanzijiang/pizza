import * as React from 'react';
import './index.scss';
import cx from 'classnames';

export interface ImageProps {
  url: string;
  isCircle?: boolean;
}

export interface ImageState { }

export default class Image extends React.PureComponent<ImageProps, ImageState> {
  render() {
    const {
      children,
      isCircle,
      url,
    } = this.props;
    return (
      <div className={cx({
        'image-wrapper': true,
        'image-wrapper_radius': isCircle,
      })}>
        {url ? <img className="image-img" src={url}/> : children}
      </div>
    );
  }
}
