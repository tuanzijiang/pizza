import * as React from 'react';
import './index.scss';
import cx from 'classnames';
import autobind from 'autobind-decorator';
import { animation_timer } from '@src/ui/base';

enum AssetsState {
  FIX = 'FIX',
  ANIMATION = 'ANIMATION',
}

enum PageState {
  FROM = 'FROM',
  TO = 'TO',
  HIDDEN = 'HIDDEN',
}

export enum OpenType {
  LEFT = 'LEFT',
  RIGHT = 'RIGHT',
}

export interface PageAssetsProps {
  init: number;
  changeCb?: (idx: number, ...extraInfo: any[]) => void;
}

export interface PageAssetsState {
  pageStates: PageState[];
  assetsState: AssetsState;
  openType: OpenType;
}

export default class PageAssets extends React.PureComponent<PageAssetsProps, PageAssetsState> {
  private pageAssets: HTMLDivElement[] = [];
  private enterAnimationTimer: any = null;
  private leaveAnimationTimer: any = null;

  static defaultProps = {
    init: 0,
  };

  constructor(props: PageAssetsProps) {
    super(props);
    const { init, children } = this.props;
    this.state = {
      pageStates: React.Children.map(children, (child, i) =>
        i === init ? PageState.FROM : PageState.HIDDEN),
      assetsState: AssetsState.FIX,
      openType: OpenType.LEFT,
    };
  }

  @autobind
  openByName(pageIdx: number, openType?: OpenType, ...extraInfo: any[]) {
    const { pageStates, assetsState } = this.state;
    const { changeCb } = this.props;
    const to = pageIdx;

    // 如果处于动画状态
    if (assetsState === AssetsState.ANIMATION) {
      return;
    }

    // 上一次的进入还没有结束，无法进入
    if (this.leaveAnimationTimer || this.enterAnimationTimer) {
      return;
    }

    // 要进入的页面不在范围内，无法进入
    if (to >= pageStates.length) {
      return;
    }

    // 进入的页面和离开的页面一致，无法进入
    if (pageStates[to] === PageState.FROM) {
      return;
    }

    // 将要进入的页面移动到相应的地方
    const newPageStates = [...pageStates];
    newPageStates[to] = PageState.TO;
    this.setState({
      openType: openType || OpenType.RIGHT,
      pageStates: newPageStates,
    });

    // 进入动画状态
    this.enterAnimationTimer = setTimeout(() => {
      this.setState({
        assetsState: AssetsState.ANIMATION,
      });
      clearTimeout(this.enterAnimationTimer);
      this.enterAnimationTimer = null;
    }, 200);

    // 恢复初始状态
    this.leaveAnimationTimer = setTimeout(() => {
      const { pageStates } = this.state;
      const newPageStates = pageStates.map(v => (
        v === PageState.TO ? PageState.FROM : PageState.HIDDEN
      ));
      this.setState({
        pageStates: newPageStates,
        assetsState: AssetsState.FIX,
      });
      clearTimeout(this.leaveAnimationTimer);
      this.leaveAnimationTimer = null;

      //  触发change钩子
      if (changeCb) {
        changeCb(pageIdx, ...extraInfo);
      }
    }, animation_timer * 2);
  }

  componentWillUnmount() {
    clearTimeout(this.leaveAnimationTimer);
    this.leaveAnimationTimer = null;
    clearTimeout(this.enterAnimationTimer);
    this.enterAnimationTimer = null;
  }

  render() {
    const { children } = this.props;
    const { assetsState, pageStates, openType } = this.state;

    const isAnimation = assetsState === AssetsState.ANIMATION;
    const isFromLeft = openType === OpenType.LEFT;
    const isFromRight = openType === OpenType.RIGHT;
    return (
      <div className="pageAssets-wrapper">
        {isAnimation && <div className="pageAssets-page_mask" />}
        {React.Children.map(children, (child, i) => {
          const isFrom = pageStates[i] === PageState.FROM;
          const isHidden = pageStates[i] === PageState.HIDDEN;
          const isToLeft = pageStates[i] === PageState.TO && openType === OpenType.LEFT;
          const isToRight = pageStates[i] === PageState.TO && openType === OpenType.RIGHT;

          const pageclassname = cx({
            'pageAssets-page': true,
            'pageAssets-page_hidden': isHidden,
            'pageAssets-page_from': !isAnimation && isFrom,
            'pageAssets-page_left': !isAnimation && isToLeft,
            'pageAssets-page_right': !isAnimation && isToRight,
            'pageAssets-page_fromAnimation_left': isAnimation && isFrom && isFromLeft,
            'pageAssets-page_fromAnimation_right': isAnimation && isFrom && isFromRight,
            'pageAssets-page_leftAnimation': isAnimation && isToLeft,
            'pageAssets-page_rightAnimation': isAnimation && isToRight,
          });
          return (
            <div
              className={pageclassname}
              ref={el => { this.pageAssets[i] = el; }}
            >
              {child}
            </div>
          );
        })}
      </div>
    );
  }
}
