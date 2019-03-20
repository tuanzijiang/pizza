import * as React from 'react';
import './index.scss';
import { connect } from 'react-redux';
import cx from 'classnames';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';
import { fetchUserApi } from '@src/services/api-fetch-user';
import autobind from 'autobind-decorator';
import Menu from './Menu';
import Order from './Order';
import { CART_ORDER_ID } from '@src/entity/Order';

export enum PageName {
  MENU = 1,
  ORDER = 2,
}

interface MainAssetsProps {
  loginStore: any;
  entityStore: any;
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
  handlePageChange(navIdx: PageName) {
    return () => {
      this.setState({
        navIdx,
      });
    };
  }

  componentDidMount() {
    fetchUserApi({
      userId: 123,
    });
  }

  render() {
    const { entityStore } = this.props;
    const { pizzas, addresses, orders, user } = entityStore;
    const { navIdx } = this.state;
    const menu = orders[CART_ORDER_ID];

    return (
      <div className="mainAssets">
        <div className="mainAssets-wrapper">
          <div className="mainAssets-header">
            <div className="mainAssets-logo">
              {i18n('Pizza Express')}
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
            {navIdx === PageName.MENU && <Menu menu={menu} pizzas={pizzas} />}
            {navIdx === PageName.ORDER && <Order entityStore={entityStore} />}
          </div>
        </div>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    loginStore: state.pageMobile.login,
    entityStore: state.entity,
  };
}, (dispatch) => {
  return {
  };
})(MainAssets);
