import * as React from 'react';
import cx from 'classnames';
import './index.scss';
import Banner from '@biz-components/Banner';
import Footer from '@biz-components/Footer';
import Menu from './Menu';
import { Order, CART_ORDER_ID } from '@entity/Order';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { MainAssetName } from '../index';
import { vw, blue_2, white_1 } from '@ui/base';
import Icon from '@biz-components/Icon';
import autobind from 'autobind-decorator';
import { neetStatusBar } from '@utils/device';

interface MainProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
  navIdx: number;
  updateNavIdx: (idx: number) => void;
  entityStore: any;
}

interface MainState { }

export default class Login extends React.PureComponent<MainProps, MainState> {
  constructor(props: MainProps) {
    super(props);
    this.state = {};
  }

  @autobind
  handleIconClick(navIdx: number) {
    const { updateNavIdx } = this.props;
    return () => {
      updateNavIdx(navIdx);
    };
  }

  renderMiddle() {
    const { navIdx } = this.props;
    return (
      <>
        {navIdx === 0 && <span className="main-middle_other">{i18n('选择披萨')}</span>}
        {navIdx === 1 && <span className="main-middle_other">{i18n('我的订单')}</span>}
        {navIdx === 2 && <span className="main-middle_other">{i18n('购物车')}</span>}
        {navIdx === 3 && <span className="main-middle_other">{i18n('我的')}</span>}
      </>
    );
  }

  render() {
    const { navIdx, entityStore } = this.props;
    const { pizzas, addresses, orders, user } = entityStore;
    const menu = orders[CART_ORDER_ID];

    return (
      <div className="main-wrapper">
        <Banner
          left={null}
          right={null}
          middle={this.renderMiddle()}
        />
        <div className={cx({
          'main-content': true,
          'main-content_status': neetStatusBar,
        })}>
          <div className={cx({
            'main-pageWrapper': true,
            'main-pageWrapper_active': navIdx === 0,
          })}>
            <Menu pizzas={pizzas} menu={menu} />
          </div>
        </div>
        <Footer height={vw(50)} wrapperClass="main-footer">
          <div className="main-footerItem" onClick={this.handleIconClick(0)}>
            {
              navIdx === 0 ?
                <Icon name="menu-fill" classnames="main-footerItem_active" /> :
                <Icon name="menu" classnames="main-footerItem_inActive" />
            }
            {
              navIdx === 0 ?
                <div className="main-footerFont_active">{i18n('菜单')}</div> :
                <div className="main-footerFont_inActive">{i18n('菜单')}</div>
            }
          </div>
          <div className="main-footerItem" onClick={this.handleIconClick(1)}>
            {
              navIdx === 1 ?
                <Icon name="shopping-fill" classnames="main-footerItem_active" /> :
                <Icon name="shopping" classnames="main-footerItem_inActive" />
            }
            {
              navIdx === 1 ?
                <div className="main-footerFont_active">{i18n('购物车')}</div> :
                <div className="main-footerFont_inActive">{i18n('购物车')}</div>
            }
          </div>
          <div className="main-footerItem" onClick={this.handleIconClick(2)}>
            {
              navIdx === 2 ?
                <Icon name="order-fill" classnames="main-footerItem_active" /> :
                <Icon name="order" classnames="main-footerItem_inActive" />
            }
            {
              navIdx === 2 ?
                <div className="main-footerFont_active">{i18n('订单')}</div> :
                <div className="main-footerFont_inActive">{i18n('订单')}</div>
            }
          </div>
          <div className="main-footerItem" onClick={this.handleIconClick(3)}>
            {
              navIdx === 3 ?
                <Icon name="me-fill" classnames="main-footerItem_active" /> :
                <Icon name="me" classnames="main-footerItem_inActive" />
            }
            {
              navIdx === 3 ?
                <div className="main-footerFont_active">{i18n('我的')}</div> :
                <div className="main-footerFont_inActive">{i18n('我的')}</div>
            }
          </div>
        </Footer>
      </div>
    );
  }
}
