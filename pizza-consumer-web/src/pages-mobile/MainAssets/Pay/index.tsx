import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { MainAssetName } from '../index';
import autobind from 'autobind-decorator';
import { neetStatusBar } from '@utils/device';
import cx from 'classnames';
import { CART_ORDER_ID } from '@entity/Order';
import { timerFormater } from '@utils/time';
import Icon from '@biz-components/Icon';
import { fetchOrderApi } from '@src/services/api-fetch-order';
import history from '@utils/history';
import { payOrderApi } from '@services/api-pay-order';
import { openToast } from '@src/utils/store';
import { PayType } from '@src/net/api-pay-order';

enum PAY_KIND {
  ALIPAY = 0,
  WECHATE = 1,
}

const pay_config = [{
  id: PAY_KIND.ALIPAY,
  title: <div>支付宝</div>,
}, {
  id: PAY_KIND.WECHATE,
  title: <div>微信支付</div>,
}];

interface PayProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
  entityStore: any;
  commonStore: any;
}

interface PayState {
  pay_kind: PAY_KIND;
  currOrderId: string;
  now_time: number;
}

export default class Pay extends React.PureComponent<PayProps, PayState> {
  private timer: any = null;

  constructor(props: PayProps) {
    super(props);
    this.state = {
      pay_kind: PAY_KIND.ALIPAY,
      currOrderId: '',
      now_time: new Date().valueOf(),
    };
  }

  componentDidMount() {
    this.timer = setInterval(() => {
      this.setState({
        now_time: new Date().valueOf(),
      });
    }, 1000);
  }

  componentWillUnmount() {
    clearInterval(this.timer);
  }

  @autobind
  handleOrderClick() {

  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    const { currOrderId } = this.state;
    onPageChange(MainAssetName.OrderDetail, OpenType.LEFT, currOrderId);
  }

  @autobind
  componentDidEnter(...extraInfo: any[]) {
    const { commonStore, entityStore } = this.props;
    const { currOrderId } = extraInfo[0];
    const { user } = entityStore;
    const orderId = currOrderId === CART_ORDER_ID ? commonStore.cart_id : currOrderId;

    this.setState({
      currOrderId: orderId,
    });

    fetchOrderApi({
      orderId,
      userId: user.id,
    });
  }

  @autobind
  handlePayKindClick(pay_kind: number) {
    return () => {
      this.setState({
        pay_kind,
      });
    };
  }

  @autobind
  async handlePayClick() {
    const { entityStore } = this.props;
    const { currOrderId } = this.state;
    const { orders, pizzas } = entityStore;
    const currOrder = orders[CART_ORDER_ID];

    let price = 0;
    if (currOrder && currOrder.num) {
      const { num } = currOrder;
      price = Object.entries(num).reduce((prev, [pizzaId, count]) => {
        const currPizza = pizzas[parseInt(pizzaId, 10)];
        const perPrice = currPizza.price;
        return prev + (count as number) * perPrice;
      }, 0);
    }
    const result = await payOrderApi({
      orderId: currOrderId,
      totalPrice: price,
      type: PayType.MOBILE,
    });
    if (!result) {
      openToast('支付失败');
    }
  }

  renderMiddle() {
    return <span className="pay-middle">{i18n('在线支付')}</span>;
  }

  renderPayKind() {
    const { pay_kind } = this.state;
    return <>
      {
        pay_config.map(v => {
          const id = v.id;
          const title = v.title;
          return <div className="pay-kindItem" key={id}
            onClick={this.handlePayKindClick(id)}
          >
            <div className="pay-kindName">
              {title}
            </div>
            <div className="pay-kindStatus">
              {pay_kind === id && <Icon name="success" classnames="pay-kindSuccess" />}
            </div>
          </div>;
        })
      }
    </>;
  }

  renderPayInfo() {
    const { entityStore } = this.props;
    const { orders, pizzas } = entityStore;
    const currOrder = orders[CART_ORDER_ID];

    const pizzaNum = currOrder.pizzas.length;
    const firstPizza = pizzas[currOrder.pizzas[0]];

    let price = 0;
    if (currOrder && currOrder.num) {
      const { num } = currOrder;
      price = Object.entries(num).reduce((prev, [pizzaId, count]) => {
        const currPizza = pizzas[parseInt(pizzaId, 10)];
        const perPrice = currPizza.price;
        return prev + (count as number) * perPrice;
      }, 0);
    }

    return <div className="pay-info">
      <div className="pay-name">
        {firstPizza && firstPizza.name}
      </div>
      <div className="pay-name">{i18n('共')}{pizzaNum}{i18n('件商品')}</div>
      <div className="pay-price">
        <span>{i18n('¥')}</span>
        {price}
      </div>
    </div>;
  }

  render() {
    const { entityStore } = this.props;
    const { orders } = entityStore;
    const { now_time, currOrderId } = this.state;

    const currOrder = orders[currOrderId];
    let startTime = (new Date).valueOf();

    if (currOrder && currOrder.startTime) {
      startTime = currOrder.startTime;
    }

    let remainTime = startTime + 15 * 60 * 1000 - now_time;
    remainTime = remainTime >= 0 ? remainTime : 0;
    const { second, minu } = timerFormater(remainTime);
    const secondFormater = second < 10 ? `0${second}` : second;
    const minFormater = minu < 10 ? `0${minu}` : minu;

    return (
      <div className="pay-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className={cx({
          'pay-content': true,
          'pay-content_status': neetStatusBar,
        })}>
          <div className="pay-time">
            <div className="pay-timePrompt">{i18n('支付剩余时间')}</div>
            <div className="pay-timeDetail">
              {minFormater}
              <span>:</span>
              {secondFormater}
            </div>
          </div>
          {currOrder && this.renderPayInfo()}
          <div className="pay-prompt">
            {i18n('选择支付方式')}
          </div>
          {this.renderPayKind()}
          <div className="pay-button" onClick={this.handlePayClick}>
            {i18n('支付')}
          </div>
        </div>
      </div>
    );
  }
}
