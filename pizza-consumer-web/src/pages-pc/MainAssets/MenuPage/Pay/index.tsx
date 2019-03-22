import * as React from 'react';
import './index.scss';
import { Order, Pizza, Address } from '@src/entity';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';
import Modal from '@components/Modal';
import autobind from 'autobind-decorator';
import cx from 'classnames';

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
  addressIds: number[];
  addresses: {
    [key: number]: Address;
  };
}

interface PayState {
  pay_kind: PAY_KIND;
  address_id: number;
  modalVisible: boolean;
  tmp_address_id: number;
}

export default class Pay extends React.PureComponent<PayProps, PayState> {
  constructor(props: PayProps) {
    super(props);
    this.state = {
      pay_kind: PAY_KIND.ALIPAY,
      address_id: 0,
      modalVisible: false,
      tmp_address_id: 0,
    };
  }

  componentDidMount() {
  }

  @autobind
  handleModalOpen() {
    const { address_id } = this.state;
    const { addressIds } = this.props;
    this.setState({
      modalVisible: true,
      tmp_address_id: address_id === 0 ? addressIds[0] : address_id,
    });
  }

  @autobind
  handleModalCancel() {
    this.setState({
      modalVisible: false,
    });
  }

  @autobind
  handleModalOk() {
    const { tmp_address_id } = this.state;
    this.setState({
      modalVisible: false,
      address_id: tmp_address_id,
    });
  }

  @autobind
  handleAddressItemClick(addressId: number) {
    return () => {
      this.setState({
        tmp_address_id: addressId,
      });
    };
  }

  renderAddress() {
    const { addressIds, addresses } = this.props;
    const { address_id } = this.state;
    let currAddress: Address;
    if (address_id === 0) {
      currAddress = addresses[addressIds[0]];
    } else {
      currAddress = addresses[address_id];
    }

    return <>
      <div className="pay-addressShown">
        {
          currAddress && <div className="pay-addressItem">
            <div className="pay-addressInfo">{currAddress.address}</div>
            <div className="pay-addressDetail">{currAddress.addressDetail}</div>
            <div className="pay-addressUser">
              <span>{currAddress.name}</span>
              <span>{currAddress.phone}</span>
            </div>
          </div>
        }
      </div>
    </>;
  }

  renderModal() {
    const { addressIds, addresses } = this.props;
    const { tmp_address_id } = this.state;

    return <div className="pay-addressList">
      {
        addressIds && addressIds.map(id => {
          const addressItem = addresses[id];
          return addressItem && <div
            className={cx({
              'pay-addressListItem': true,
              'pay-addressListItem_active': tmp_address_id === id,
            })} key={id}
            onClick={this.handleAddressItemClick(id)}
          >
            <div className="pay-addressListInfo">
              {addressItem.address}
              {addressItem.addressDetail}
            </div>
            <div className="pay-addressListUser">
              <span>{addressItem.name}</span>
              <span>{addressItem.phone}</span>
            </div>
          </div>;
        })
      }
    </div>;
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
    const { modalVisible } = this.state;
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
        <Modal
          title={i18n('地址列表')} visible={modalVisible}
          handleClose={this.handleModalCancel}
          handleOk={this.handleModalOk}
        >
          {this.renderModal()}
        </Modal>
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
            <div className="pay-item pay-address">
              <div className="pay-itemHeader">{i18n('收获地址')}</div>
              <div className="pay-itemBody">
                {this.renderAddress()}
              </div>
              <div className="pay-itemFooter">
                <div className="pay-button">
                  <span onClick={this.handleModalOpen}>{i18n('查看更多')}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
