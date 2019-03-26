import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { LoginAssetName } from '../index';
import Input from '@biz-components/Input';
import autobind from 'autobind-decorator';
import { isTel } from '@src/utils/check';
import { openToast } from '@src/utils/store';
import { sendVerificationApi } from '@src/services/api-send-verification';
import { VerificationType } from '@src/net/common';

interface FindPWProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType, ...extraInfo: any[]): void;
}

interface FindPWState {
  account: string;
}

export default class FindPW extends React.PureComponent<FindPWProps, FindPWState> {
  constructor(props: FindPWProps) {
    super(props);
    this.state = {
      account: '',
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.LoginPW, OpenType.LEFT);
  }

  @autobind
  handleButtonClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    const { account } = this.state;
    if (!isTel(account)) {
      openToast('手机号格式错误');
      return;
    }

    onPageChange(LoginAssetName.SetPW, OpenType.RIGHT, account);
  }

  @autobind
  handleAccountChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        account: e.target.value,
      });
    }
  }

  renderMiddle() {
    return <span className="findPW-middle">{i18n('找回密码')}</span>;
  }

  render() {
    const { account } = this.state;
    return (
      <div className="findPW-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className="findPW-content">
          <div className="findPW-beforeInput" />
          <div className="findPW-input">
            <Input
              placeholde={i18n('手机号')}
              value={account}
              onChange={this.handleAccountChange}
            />
          </div>
          <div className="findPW-button" onClick={this.handleButtonClick}>{i18n('发送验证码')}</div>
        </div>
      </div>
    );
  }
}
