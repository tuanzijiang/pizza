import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Main from './Main';
import OrderDetail from './OrderDetail';
import Settlement from './Settlement';
import AddressList from './Address';
import AddressEdit from './AddressEdit';
import Pay from './Pay';
import { connect } from 'react-redux';
import { pageMobile as pageActionCreator, entity as entityActionCreator } from '@store/action';
import autobind from 'autobind-decorator';
import { OrderWeakSchema, OrderSchema } from '@src/entity/schema';
import history from '@src/utils/history';

interface LoginAssetsProps {
  mainStore: any;
  entityStore: any;
  commonStore: any;
  updateNavIdx: (idx: number) => void;
  updateOrder: (order: OrderWeakSchema | OrderSchema) => void;
}

interface LoginAssetsState { }

export enum MainAssetName {
  Main = 0,
  OrderDetail = 1,
  Settlement = 2,
  Pay = 3,
  AddressList = 4,
  AddressEdit = 5,
}

const config = {
  [MainAssetName.Main]: 0,
  [MainAssetName.OrderDetail]: 1,
  [MainAssetName.Settlement]: 2,
  [MainAssetName.Pay]: 3,
  [MainAssetName.AddressList]: 4,
  [MainAssetName.AddressEdit]: 5,
};

const handleTouchMove = (e: TouchEvent) => {
  e.preventDefault();
};

export class MainAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();
  private orderDetailEl: React.RefObject<OrderDetail> = React.createRef();
  private addressListEl: React.RefObject<AddressList> = React.createRef();
  private addressEditEl: React.RefObject<AddressEdit> = React.createRef();
  private mainEl: React.RefObject<Main> = React.createRef();
  private payEl: React.RefObject<Pay> = React.createRef();

  constructor(props: LoginAssetsProps) {
    super(props);
    this.state = {};
  }

  componentDidMount() {
    const { entityStore } = this.props;
    const { user } = entityStore;
    if (user.id === 0) {
      history.push('./LoginAssets');
    }
  }

  @autobind
  handlePageChange(name: MainAssetName, openType?: OpenType, ...extraInfo: any[]) {
    if (this.pageAssetsEl && this.pageAssetsEl.current) {
      this.pageAssetsEl.current.openByName(config[name], openType, ...extraInfo);
    }
  }

  @autobind
  onPageChange(idx: number, ...extraInfo: any[]) {
    if (idx === config[MainAssetName.OrderDetail] &&
      this.orderDetailEl && this.orderDetailEl.current.componentDidEnter) {
      this.orderDetailEl.current.componentDidEnter(...extraInfo);
    }
    if (idx === config[MainAssetName.AddressList] &&
      this.addressListEl && this.addressListEl.current.componentDidEnter) {
      this.addressListEl.current.componentDidEnter(...extraInfo);
    }
    if (idx === config[MainAssetName.AddressEdit] &&
      this.addressEditEl && this.addressEditEl.current.componentDidEnter) {
      this.addressEditEl.current.componentDidEnter(...extraInfo);
    }
    if (idx === config[MainAssetName.Main] &&
      this.mainEl && this.mainEl.current.componentDidEnter) {
      this.mainEl.current.componentDidEnter(...extraInfo);
    }
    if (idx === config[MainAssetName.Pay] &&
      this.payEl && this.payEl.current.componentDidEnter) {
      this.payEl.current.componentDidEnter(...extraInfo);
    }

  }

  render() {
    const { mainStore, updateNavIdx, entityStore, updateOrder, commonStore } = this.props;
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
            commonStore={commonStore}
            ref={this.mainEl}
          />
          <OrderDetail
            ref={this.orderDetailEl}
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
          />
          <Settlement
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
            commonStore={commonStore}
          />
          <Pay
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
            commonStore={commonStore}
            ref={this.payEl}
          />
          <AddressList
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
            updateOrder={updateOrder}
            ref={this.addressListEl}
          />
          <AddressEdit
            onPageChange={this.handlePageChange}
            entityStore={entityStore}
            ref={this.addressEditEl}
          />
        </PageAssets>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    mainStore: state.pageMobile.main,
    entityStore: state.entity,
    commonStore: state.common,
  };
}, (dispatch) => {
  return {
    updateNavIdx: (idx: number) => {
      dispatch(pageActionCreator.main.updateNavIdx(idx));
    },
    updateOrder: (order: OrderWeakSchema | OrderSchema) => {
      dispatch(entityActionCreator.orders.updateOrder(order));
    },
  };
})(MainAssets);
