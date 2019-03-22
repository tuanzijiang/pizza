import * as React from 'react';
import './index.scss';
import { Order, Pizza } from '@src/entity';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';

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
  menu: Order;
  pizzas: Pizza[];
  handleToMenu: () => void;
}

interface PayState {
  pay_kind: PAY_KIND;
}

export default class Pay extends React.PureComponent<PayProps, PayState> {
  constructor(props: PayProps) {
    super(props);
    this.state = {
      pay_kind: PAY_KIND.ALIPAY,
    };
  }

  componentDidMount() {
  }

  renderPayKind() {
    const { pay_kind } = this.state;
    return <>
      {
        pay_config.map(v => {
          const id = v.id;
          const title = v.title;
          return <div className="pay-kindItem" key={id}>
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

  renderPizzas() {
    const { menu, pizzas } = this.props;
    const { pizzas: pizzaIds } = menu;

    return <>
      {
        pizzaIds && pizzaIds.map((pizzaId: number) => {
          const currPizza = pizzas[pizzaId];
          const currNums = menu.num;
          const currNum = currNums[pizzaId];

          if (currNum === 0) {
            return null;
          }

          return (
            <div className="pay-pizzaItem" key={pizzaId}>
              <div className="pay-pizzaItemImage">
                <Icon name="pisa" classnames="settlement-pizzaItemPisa" />
              </div>
              <div className="pay-pizzaItemName">
                {currPizza.name}
              </div>
              <div className="pay-pizzaItemInfo">
                <div className="pay-pizzaItemNum">
                  <span>{i18n('x')}</span>
                  <span>{currNum}</span>
                </div>
                <div className="pay-pizzaItemPrice">
                  <span>{i18n('¥')}</span>
                  <span>{currPizza.price}</span>
                </div>
              </div>
            </div>
          );
        })
      }
    </>;
  }

  render() {
    const { handleToMenu, menu, pizzas } = this.props;
    let price = 0;
    if (menu && menu.num) {
      const { num } = menu;
      price = Object.entries(num).reduce((prev, [pizzaId, count]) => {
        const currPizza = pizzas[parseInt(pizzaId, 10)];
        const perPrice = currPizza.price;
        return prev + (count as number) * perPrice;
      }, 0);
    }
    return (
      <div className="pay-wrapper">
        <div className="pay-main">
          <div className="pay-back" onClick={handleToMenu}>{i18n('< 返回商家')}</div>
          <div className="pay-info">
            <div className="pay-item pay-list">
              <div className="pay-itemHeader">{i18n('商品详情')}</div>
              <div className="pay-itemBody">
                {menu && this.renderPizzas()}
              </div>
              <div className="pay-itemFooter">
                {i18n('小计 ¥')}
                <span>{price}</span>
              </div>
            </div>
            <div className="pay-item pay-kinds">
              <div className="pay-itemHeader">{i18n('付款方式')}</div>
              <div className="pay-itemBody">
                {this.renderPayKind()}
              </div>
              <div className="pay-itemFooter">
                <div className="pay-button">
                  {i18n('支付')}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
