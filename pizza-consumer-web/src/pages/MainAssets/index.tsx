import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Main from './Main';
import OrderDetail from './OrderDetail';
import Settlement from './Settlement';
import Pay from './Pay';
import { connect } from 'react-redux';
import { page as pageActionCreator } from '@store/action';
import autobind from 'autobind-decorator';

interface LoginAssetsProps {
  mainStore: any;
  entityStore: any;
  updateNavIdx: (idx: number) => void;
}

interface LoginAssetsState { }

export enum MainAssetName {
  Main = 0,
  OrderDetail = 1,
  Settlement = 2,
  Pay = 3,
}

const config = {
  [MainAssetName.Main]: 0,
  [MainAssetName.OrderDetail]: 1,
  [MainAssetName.Settlement]: 2,
  [MainAssetName.Pay]: 3,
};

const handleTouchMove = (e: TouchEvent) => {
  e.preventDefault();
};

export class MainAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();
  private orderDetailEl: React.RefObject<OrderDetail> = React.createRef();

  constructor(props: LoginAssetsProps) {
    super(props);
    this.state = {};
  }

  @autobind
  handlePageChange(name: MainAssetName, openType?: OpenType, ...extraInfo: any[]) {
    if (this.pageAssetsEl && this.pageAssetsEl.current) {
      this.pageAssetsEl.current.openByName(config[name], openType, ...extraInfo);
    }
  }

  componentDidMount() {
  }

  componentWillUnmount() {
  }

  @autobind
  onPageChange(idx: number, ...extraInfo: any[]) {
    if (idx === config[MainAssetName.OrderDetail] &&
      this.orderDetailEl && this.orderDetailEl.current.componentDidEnter) {
      this.orderDetailEl.current.componentDidEnter(...extraInfo);
    }
  }

  render() {
    const { mainStore, updateNavIdx, entityStore } = this.props;
    const { navIdx, orderIds } = mainStore;
    return (
      <div className="loginAssets">
        <PageAssets init={MainAssetName.Main} ref={this.pageAssetsEl} changeCb={this.onPageChange}>
          <Main
            onPageChange={this.handlePageChange}
            navIdx={navIdx}
            orderIds={orderIds}
            updateNavIdx={updateNavIdx}
            entityStore={entityStore}
          />
          <OrderDetail
            ref={this.orderDetailEl}
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
          />
          <Settlement
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
          />
          <Pay
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
          />
        </PageAssets>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    mainStore: state.page.main,
    entityStore: state.entity,
  };
}, (dispatch) => {
  return {
    updateNavIdx: (idx: number) => {
      dispatch(pageActionCreator.main.updateNavIdx(idx));
    },
  };
})(MainAssets);
