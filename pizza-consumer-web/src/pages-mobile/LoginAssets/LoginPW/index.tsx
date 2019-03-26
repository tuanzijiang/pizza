import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { LoginAssetName } from '../index';
import Input from '@biz-components/Input';
import autobind from 'autobind-decorator';
import { isTel, isPW } from '@utils/check';
import { loginApi } from '@services/api-login';
import { LoginType } from '@src/net/common';
import history from '@utils/history';
import { openToast } from '@utils/store';

interface LoginPWProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
}

interface LoginPWState {
  account: string;
  password: string;
}

export default class LoginPW extends React.PureComponent<LoginPWProps, LoginPWState> {
  constructor(props: LoginPWProps) {
    super(props);
    this.state = {
      account: '',
      password: '',
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.Login, OpenType.LEFT);
  }

  @autobind
  handleFindPWClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.FindPW, OpenType.RIGHT);
  }

  @autobind
  handleAccountChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        account: e.target.value,
      });
    }
  }

  @autobind
  handlePasswordChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        password: e.target.value,
      });
    }
  }

  @autobind
  handleLoginClick() {
    const { account, password } = this.state;
    if (!isTel(account)) {
      openToast('手机号格式错误');
      return;
    }
    if (!isPW(password)) {
      openToast('密码格式错误');
      return;
    }
    this.handleLogin();
  }

  @autobind
  async handleLogin() {
    const { account, password } = this.state;
    const result = await loginApi({
      account,
      password,
      type: LoginType.PASSWORD,
    });
    if (result) {
      history.push('./MainAssets');
    } else {
      openToast('密码错误');
    }
  }

  renderMiddle() {
    return <span className="loginPW-middle">{i18n('密码登录')}</span>;
  }

  render() {
    const { account, password } = this.state;
    return (
      <div className="loginPW-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className="loginPW-content">
          <div className="loginPW-beforeInput" />
          <div className="loginPW-input">
            <Input
              placeholde={i18n('手机号/邮箱')}
              onChange={this.handleAccountChange}
              value={account}
            />
          </div>
          <form className="loginPW-input">
            <Input
              placeholde={i18n('密码')}
              type="password"
              onChange={this.handlePasswordChange}
              value={password}
            />
          </form>
          <div className="loginPW-button"
            onClick={this.handleLoginClick}>{i18n('登录')}</div>
          <div className="loginPW-afterInput">
            <span onClick={this.handleFindPWClick}>{i18n('找回密码')}</span>
          </div>
        </div>
      </div>
    );
  }
}
