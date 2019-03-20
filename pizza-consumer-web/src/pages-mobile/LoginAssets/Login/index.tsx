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
import history from '@utils/history';

interface LoginProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
  onVarifyClick: (varifyTime: number) => void;
  varifyTime: number;
  currentTime: number;
}

interface LoginState {
  account: string;
  varify: string;
}

const VARIFY_TIME = 1000 * 45;

export default class Login extends React.PureComponent<LoginProps, LoginState> {
  constructor(props: LoginProps) {
    super(props);
    this.state = {
      account: '',
      varify: '',
    };
  }

  @autobind
  handleRightClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.LoginPW);
  }

  @autobind
  handleRegisterClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.Register);
  }

  renderMiddle() {
    return <span className="login-middle">{i18n('登录')}</span>;
  }

  renderRight() {
    return <span className="login-right">{i18n('密码登录')}</span>;
  }

  @autobind
  handleVarifyClick() {
    const { varifyTime, onVarifyClick, currentTime } = this.props;
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
  handleLoginClick() {
    history.push('./MainAssets');
  }

  renderVarifyCode() {
    const { varifyTime, currentTime } = this.props;
    const text = !varifyTime ? i18n('获取验证码') : i18n('重新获取');

    if (!varifyTime || varifyTime - currentTime < 0) {
      return (
        <div
          className="login-varigy_ready"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="login-varigy_wait">
          <span>{i18n('已发送:')}</span>
          <span>{second}</span>
          <span>{i18n('s')}</span>
        </div>
      );
    }
  }

  render() {
    const loginContent = cx({
      'login-content': true,
      'login-content_status': neetStatusBar,
    });
    const { varify, account } = this.state;

    return (
      <div className="login-wrapper">
        <Banner
          left={null}
          right={this.renderRight()}
          middle={this.renderMiddle()}
          rightClick={this.handleRightClick}
        />
        <div className={loginContent}>
          <div className="login-beforeInput" />
          <div className="login-input">
            <Input
              placeholde={i18n('手机号')}
              right={this.renderVarifyCode()}
              value={account}
              onChange={this.handleAccountChange}
            />
          </div>
          <div className="login-input">
            <Input
              placeholde={i18n('验证码')}
              type="number"
              value={varify}
              onChange={this.handleVarifyChange}
            />
          </div>
          <div className="login-afterInput">
            <span>{i18n('没有账号？')}</span>
            <span
              className="login-toRegister"
              onClick={this.handleRegisterClick}
            >
              {i18n('注册')}
            </span>
          </div>
          <div
            className="login-button"
            onClick={this.handleLoginClick}
          >
            {i18n('登录')}
          </div>
        </div>
      </div>
    );
  }
}
