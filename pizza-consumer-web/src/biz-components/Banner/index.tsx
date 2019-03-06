import * as React from 'react';
import autobind from 'autobind-decorator';
import cx from 'classnames';
import Icon from '@biz-components/Icon';
import { neetStatusBar } from '@utils/device';
import './index.scss';

export interface BannerProps {
  left?: React.ReactNode;
  leftClick?: (e?: React.MouseEvent<HTMLDivElement>) => void;
  right?: React.ReactNode;
  rightClick?: (e?: React.MouseEvent<HTMLDivElement>) => void;
  middle?: React.ReactNode;
  middleClick?: (e?: React.MouseEvent<HTMLDivElement>) => void;
  avoidTouchMove?: boolean;
  background?: string;
  wrapperClass?: string | string[];
}

export interface BannerState { }

const handleTouchMove = (e: TouchEvent) => {
  // e.preventDefault();
};

export default class Banner extends React.PureComponent<BannerProps, BannerState> {
  private bannerEl: React.RefObject<HTMLDivElement> = React.createRef();

  static defaultProps = {
    left: <Icon name="left" classnames="banner-icon" />,
    right: <Icon name="right" classnames="banner-icon" />,
    avoidTouchMove: false,
  };

  componentDidMount() {
    const { avoidTouchMove } = this.props;
    if (avoidTouchMove) {
      this.bannerEl.current.addEventListener('touchmove', handleTouchMove, { passive: false });
    }
  }

  componentWillUnmount() {
    this.bannerEl.current.removeEventListener('touchmove', handleTouchMove);
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<HTMLDivElement>) {
    const { leftClick } = this.props;
    if (leftClick) {
      leftClick(e);
    }
  }

  @autobind
  handleMiddleClick(e: React.MouseEvent<HTMLDivElement>) {
    const { middleClick } = this.props;
    if (middleClick) {
      middleClick(e);
    }
  }

  @autobind
  handleRightClick(e: React.MouseEvent<HTMLDivElement>) {
    const { rightClick } = this.props;
    if (rightClick) {
      rightClick(e);
    }
  }

  render() {
    const {
      left, right, middle,
      leftClick, rightClick, middleClick,
      background, wrapperClass,
    } = this.props;
    const style = background ? { background } : {};
    const wrapperName = cx(
      'banner-wrapper',
      ...[].concat(wrapperClass),
    );
    return (
      <div
        className={wrapperName}
        id="banner-wrapper"
        ref={this.bannerEl}
      >
        {
          neetStatusBar && <div className="banner-header" style={style} />
        }
        <div className="banner-content" style={style}>
          <div
            className="banner-left"
            onClick={leftClick && this.handleLeftClick}
          >
            {left}
          </div>
          <div
            className="banner-middle"
            onClick={middleClick && this.handleMiddleClick}
          >
            {middle}
          </div>
          <div
            className="banner-right"
            onClick={rightClick && this.handleRightClick}
          >
            {right}
          </div>
        </div>
      </div>
    );
  }
}
