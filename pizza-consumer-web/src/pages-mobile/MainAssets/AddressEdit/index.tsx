import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { MainAssetName } from '../index';
import Input from '@biz-components/Input';
import autobind from 'autobind-decorator';
import { isPW, isEmail } from '@utils/check';
import { loginApi } from '@services/api-login';
import { LoginType, Sex } from '@src/net/common';
import history from '@utils/history';
import { openToast } from '@utils/store';
import { AddressUsage } from '../Address';
import cx from 'classnames';
import { string } from 'prop-types';
import { Address } from '@src/entity';
import { SexSchema } from '@src/entity/schema';
import { addAddressApi } from '@services/api-add-address';
import { updateAddressApi } from '@services/api-update-address';

interface AddressEditProps {
  onPageChange(idx: MainAssetName, openType?: OpenType, ...extraInfo: any[]): void;
  entityStore: any;
}

interface AddressEditState {
  name: string;
  sex: Sex;
  address: string;
  addressDetail: string;
  addressId: number;
  phone: string;
}

export default class AddressEdit extends React.PureComponent<AddressEditProps, AddressEditState> {
  constructor(props: AddressEditProps) {
    super(props);
    this.state = {
      name: '',
      sex: null,
      address: '',
      addressDetail: '',
      phone: '',
      addressId: null,
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(MainAssetName.AddressList, OpenType.LEFT, {
      usage: AddressUsage.EDITABLE,
    });
  }

  @autobind
  handleNameChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        name: e.target.value,
      });
    }
  }

  @autobind
  handleAddressChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        address: e.target.value,
      });
    }
  }

  @autobind
  handleAddressDetailChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        addressDetail: e.target.value,
      });
    }
  }

  @autobind
  handlePhoneChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        phone: e.target.value,
      });
    }
  }

  @autobind
  handleSexChange(sex: Sex) {
    return () => {
      this.setState({
        sex,
      });
    };
  }

  @autobind
  componentDidEnter(...extraInfo: any[]) {
    const { addressId } = extraInfo[0];
    if (!addressId) {
      return;
    }

    const { entityStore } = this.props;
    const { addresses } = entityStore;
    const currAddress = addresses[addressId];
    const { name, sex, address, addressDetail, phone } = currAddress;
    this.setState({
      addressId,
      name,
      sex,
      address,
      addressDetail,
      phone,
    });
  }

  @autobind
  async handleConfirmClick() {
    const { name, sex, address, addressDetail, phone, addressId } = this.state;
    const { entityStore, onPageChange } = this.props;
    const { user } = entityStore;
    const userId = user.id;
    const addressEntity = {
      address,
      addressDetail,
      phone,
      name,
      sex,
      id: addressId,
      tag: '',
    };
    if (addressId) {
      await updateAddressApi({
        userId,
        address: addressEntity,
      });
    } else {
      await addAddressApi({
        userId,
        address: addressEntity,
      });
    }
    onPageChange(MainAssetName.AddressList, OpenType.LEFT, {
      usage: AddressUsage.EDITABLE,
    });
  }

  renderMiddle() {
    return <span className="addressEdit-middle">{i18n('地址编辑')}</span>;
  }

  renderInputLeft() {
    const { sex } = this.state;
    return <div className="addressEdit-sexWrap">
      <div className={cx({
        'addressEdit-sexItem': true,
        'addressEdit-sexItem_active': sex === Sex.MALE,
      })} onClick={this.handleSexChange(Sex.MALE)}>{i18n('男')}</div>
      <div className={cx({
        'addressEdit-sexItem': true,
        'addressEdit-sexItem_active': sex === Sex.FEMALE,
      })} onClick={this.handleSexChange(Sex.FEMALE)}>{i18n('女')}</div>
    </div>;
  }

  render() {
    const { address, addressDetail, phone, name } = this.state;
    return (
      <div className="addressEdit-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className="addressEdit-content">
          <div className="addressEdit-beforeInput" />
          <div className="addressEdit-input">
            <Input
              placeholde={i18n('姓名')}
              onChange={this.handleNameChange}
              value={name}
            />
          </div>
          <div className="addressEdit-input">
            <Input middle={this.renderInputLeft()} />
          </div>
          <div className="addressEdit-input">
            <Input
              placeholde={i18n('电话号码')}
              onChange={this.handlePhoneChange}
              value={phone}
            />
          </div>
          <div className="addressEdit-input">
            <Input
              placeholde={i18n('地址')}
              onChange={this.handleAddressChange}
              value={address}
            />
          </div>
          <div className="addressEdit-input">
            <Input
              placeholde={i18n('详细地址')}
              onChange={this.handleAddressDetailChange}
              value={addressDetail}
            />
          </div>
          <div className="addressEdit-button"
            onClick={this.handleConfirmClick}>
            {i18n('确定')}
          </div>
        </div>
      </div>
    );
  }
}
