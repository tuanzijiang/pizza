import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { MainAssetName } from '../index';
import autobind from 'autobind-decorator';
import { neetStatusBar } from '@utils/device';
import { CART_ORDER_ID, Order } from '@entity/Order';
import { fetchAddressApi } from '@src/services/api-fetch-address';
import cx from 'classnames';
import { User } from '@src/entity';
import Icon from '@biz-components/Icon';
import { OrderWeakSchema, OrderSchema } from '@src/entity/schema';

export enum AddressUsage {
  EDITABLE = 0,
  SELECT = 1,
}

interface AddressProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
  entityStore: any;
  updateOrder: (order: OrderWeakSchema | OrderSchema) => void;
}

interface AddressState {
  usage: AddressUsage;
}

export default class Address extends React.PureComponent<AddressProps, AddressState> {
  constructor(props: AddressProps) {
    super(props);
    this.state = {
      usage: AddressUsage.SELECT,
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    const { usage } = this.state;

    if (usage === AddressUsage.SELECT) {
      onPageChange(MainAssetName.Settlement, OpenType.LEFT);
    }

    if (usage === AddressUsage.EDITABLE) {
      onPageChange(MainAssetName.Main, OpenType.LEFT);
    }
  }

  @autobind
  handleItemClick(addressId: number) {
    return () => {
      const { entityStore, updateOrder, onPageChange } = this.props;
      const { orders } = entityStore;
      const { usage } = this.state;
      const currOrder = orders[CART_ORDER_ID] || {};

      if (usage === AddressUsage.SELECT) {
        const order = currOrder.update({
          address: addressId,
        });
        updateOrder(order);
        onPageChange(MainAssetName.Settlement, OpenType.LEFT);
      }
    };
  }

  @autobind
  handleEditClick(addressId?: number) {
    return () => {
      const { onPageChange } = this.props;
      onPageChange(MainAssetName.AddressEdit, OpenType.RIGHT, {
        addressId,
      });
    };
  }

  componentDidEnter(...extraInfo: any[]) {
    const usage = extraInfo[0].usage;
    const { entityStore } = this.props;
    const { user } = entityStore;
    const { id } = user;
    this.setState({
      usage,
    });
    fetchAddressApi({
      userId: id,
    });
  }

  renderMiddle() {
    return <span className="address-middle">{i18n('选择地址')}</span>;
  }

  renderRight() {
    return <div className="address-right" onClick={this.handleEditClick()}>
      <Icon name="add" classnames="address-rightIcon"/>
    </div>;
  }

  render() {
    const { entityStore } = this.props;
    const { user, addresses } = entityStore;
    const { usage } = this.state;
    const { addresses: addressIds } = user as User;

    return (
      <div className="address-wrapper">
        <Banner
          right={this.renderRight()}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className={cx({
          'address-content': true,
          'address-content_status': neetStatusBar,
        })}>
          {
            addressIds.map(v => {
              const currAddress = addresses[v];
              return currAddress && <div
                className="address-item" key={v}
                onClick={this.handleItemClick(v)}
              >
                <div className="address-itemLeft">
                  <div className="address-poiInfo">
                    {currAddress.address}
                    {currAddress.addressDetail}
                  </div>
                  <div className="address-userInfo">
                    {currAddress.name}
                    <span>{currAddress.phone}</span>
                  </div>
                </div>
                <div className="address-itemRight">
                  {usage === AddressUsage.EDITABLE && <Icon
                    name="edit"
                    onClick={this.handleEditClick(v)}
                  />}
                </div>
              </div>;
            })
          }
        </div>
      </div>
    );
  }
}
