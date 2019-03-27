import * as React from 'react';
import cx from 'classnames';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import Input from '@biz-components/Input';
import i18n from '@utils/i18n';
import { LoginAssetName } from '../index';
import { neetStatusBar } from '@utils/device';
import autobind from 'autobind-decorator';
import { timerFormater } from '@utils/time';
import { registerApi } from '@services/api-register';
import history from '@utils/history';
import { openToast } from '@utils/store';
import { isTel, isVarify } from '@utils/check';

interface RegisterProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
  onVarifyClick: (varifyTime: number) => void;
  varifyTime: number;
  currentTime: number;
}

interface RegisterState {
  account: string;
  varify: string;
}

const VARIFY_TIME = 1000 * 45;

export default class Register extends React.PureComponent<RegisterProps, RegisterState> {
  private varifyTimer: any = null;

  constructor(props: RegisterProps) {
    super(props);
    this.state = {
      account: '',
      varify: '',
    };
  }

  @autobind
  handleRightClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.RegisterEmail);
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.Login, OpenType.LEFT);
  }

  renderMiddle() {
    return <span className="register-middle">{i18n('注册')}</span>;
  }

  renderRight() {
    return <span className="register-right">{i18n('邮箱注册')}</span>;
  }

  @autobind
  handleVarifyClick() {
    const { currentTime, varifyTime, onVarifyClick } = this.props;
    if (varifyTime < currentTime) {
      onVarifyClick(new Date().valueOf() + VARIFY_TIME);
    }
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
  handleVarifyChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        varify: e.target.value,
      });
    }
  }

  @autobind
  handleSignUpClick() {
    const { account, varify } = this.state;
    if (!isTel(account)) {
      openToast('手机号格式错误');
      return;
    }
    if (!isVarify(varify)) {
      openToast('验证码格式错误');
      return;
    }
    this.handleSignUp();
  }

  @autobind
  async handleSignUp() {
    const { account, varify } = this.state;
    const result = await registerApi({
      phone: account,
      password: varify,
    });
    if (result) {
      history.push('./MainAssets');
    } else {
      openToast('密码错误');
    }
  }

  renderVarifyCode() {
    const { varifyTime, currentTime } = this.props;
    const text = !varifyTime ? i18n('获取验证码') : i18n('重新获取');

    if (!varifyTime || varifyTime - currentTime < 0) {
      return (
        <div
          className="register-varigy_ready"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="register-varigy_wait">
          <span>{i18n('已发送:')}</span>
          <span>{second}</span>
          <span>{i18n('s')}</span>
        </div>
      );
    }
  }

  render() {
    const registerContent = cx({
      'register-content': true,
      'register-content_status': neetStatusBar,
    });
    const { varify, account } = this.state;

    return (
      <div className="register-wrapper">
        <Banner
          right={this.renderRight()}
          middle={this.renderMiddle()}
          rightClick={this.handleRightClick}
          leftClick={this.handleLeftClick}
        />
        <div className={registerContent}>
          <div className="register-beforeInput" />
          <div className="register-input">
            <Input
              placeholde={i18n('手机号')}
              right={this.renderVarifyCode()}
              value={account}
              onChange={this.handleAccountChange}
            />
          </div>
          <div className="register-input">
            <Input
              placeholde={i18n('验证码')}
              type="number"
              value={varify}
              onChange={this.handleVarifyChange}
            />
          </div>
          <div className="register-afterInput">
            <span>{i18n('没有账号？')}</span>
            <span
              className="register-toRegister"
              onClick={this.handleLeftClick}
            >
              {i18n('登录')}
            </span>
          </div>
          <div className="register-button" onClick={this.handleSignUpClick}>{i18n('注册')}</div>
        </div>
      </div>
    );
  }
}
