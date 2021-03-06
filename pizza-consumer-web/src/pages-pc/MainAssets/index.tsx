import * as React from 'react';
import './index.scss';
import { connect } from 'react-redux';
import cx from 'classnames';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';
import { entity as entityActionCreator } from '@store/action';
import autobind from 'autobind-decorator';
import MenuPage from './MenuPage';
import Order from './Order';
import { CART_ORDER_ID } from '@src/entity/Order';
import history from '@src/utils/history';
import { fetch, OBJECT_STORE_NAMES } from '@src/utils/db';
import store from '@src/store';
import { logoutApi } from '@src/services/api-logout';

export enum PageName {
  MENU = 1,
  ORDER = 2,
}

interface MainAssetsProps {
  mainStore: any;
  entityStore: any;
  commonStore: any;
}

interface MainAssetsState {
  navIdx: number;
}

export class MainAssets extends React.PureComponent<MainAssetsProps, MainAssetsState> {
  constructor(props: MainAssetsProps) {
    super(props);
    this.state = {
      navIdx: PageName.MENU,
    };
  }

  @autobind
  handleLogoutClick(e: React.MouseEvent) {
    logoutApi();
    history.push('./LoginAssets');
  }

  @autobind
  handlePageChange(navIdx: PageName) {
    return () => {
      this.setState({
        navIdx,
      });
    };
  }

  componentDidMount() {
    const { entityStore } = this.props;
    const { user } = entityStore;

    if (user.id === 0) {
      let shouldLogout = true;

      fetch(OBJECT_STORE_NAMES.USER, (obj) => {
        shouldLogout = false;
        store.dispatch(entityActionCreator.user.updateUser({
          ...obj,
        }));
      }, () => {
        if (shouldLogout) {
          history.push('./LoginAssets');
        }
      });
    }
  }

  render() {
    const { entityStore, mainStore, commonStore } = this.props;
    const { pizzas, addresses, orders, user } = entityStore;
    const { orderIds } = mainStore;
    const { navIdx } = this.state;
    const { cart_id } = commonStore;
    const menu = orders[CART_ORDER_ID];

    return (
      <div className="mainAssets">
        <div className="mainAssets-wrapper">
          <div className="mainAssets-header">
            <div className="mainAssets-logo">
              <span onClick={this.handleLogoutClick}>{i18n('Pizza Express')}</span>
              <div
                className={cx({
                  'mainAssets-button': true,
                  'mainAssets-button_active': navIdx === PageName.MENU,
                })}
                onClick={this.handlePageChange(PageName.MENU)}
              >
                {i18n('菜单')}
              </div>
              <div
                className={cx({
                  'mainAssets-button': true,
                  'mainAssets-button_active': navIdx === PageName.ORDER,
                })}
                onClick={this.handlePageChange(PageName.ORDER)}
              >
                {i18n('订单')}
              </div>
            </div>
            <div className="mainAssets-userInfo">
              <Icon name="me" classnames="mainAssets-avatar_default" />
              <span className="mainAssets-userName">{i18n(user.name)}</span>
            </div>
          </div>
          <div className="mainAssets-content">
            {user && user.id && navIdx === PageName.MENU && <MenuPage
              menu={menu} pizzas={pizzas} addresses={addresses} user={user} cartId={cart_id} />}
            {user && user.id && navIdx === PageName.ORDER && <Order
              orders={orders} addresses={addresses} orderIds={orderIds}
              pizzas={pizzas} user={user}
            />}
          </div>
        </div>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    mainStore: state.pagePc.main,
    entityStore: state.entity,
    commonStore: state.common,
  };
}, (dispatch) => {
  return {
  };
})(MainAssets);
