import * as React from 'react';
import cx from 'classnames';
import './index.scss';

export interface FooterProps {
  height: string;
  wrapperClass?: string | string[];
}

export interface FooterState { }

const handleTouchMove = (e: TouchEvent) => {
  // e.preventDefault();
};

export default class Footer extends React.PureComponent<FooterProps, FooterState> {
  private footerEl: React.RefObject<HTMLDivElement> = React.createRef();

  componentDidMount() {
    this.footerEl.current.addEventListener('touchmove', handleTouchMove, { passive: false });
  }

  componentWillUnmount() {
    this.footerEl.current.removeEventListener('touchmove', handleTouchMove);
  }

  render() {
    const { height, wrapperClass } = this.props;
    const style = {
      height,
    };
    const footerName = cx(
      'footer-wrapper',
      ...[].concat(wrapperClass),
    );
    return (
      <div
        className={footerName}
        ref={this.footerEl}
        style={style}
      >
        {this.props.children}
      </div>
    );
  }
}
