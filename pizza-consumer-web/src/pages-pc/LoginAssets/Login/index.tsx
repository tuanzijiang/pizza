import * as React from 'react';
import './index.scss';
import cx from 'classnames';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import { timerFormater } from '@utils/time';
import { PageName } from '..';
import history from '@utils/history';
import { LoginType, VerificationType } from '@src/net/common';
import { loginApi } from '@services/api-login';
import { openToast } from '@utils/store';
import { isTel, isVarify, isEmail, isPW } from '@utils/check';
import { sendVerificationApi } from '@src/services/api-send-verification';

interface LoginProps {
  currentTime: number;
  loginStore: any;
  onVarifyClick: (varifyTime: number) => void;
  handlePageChange: (pagename: PageName) => void;
}

interface LoginState {
  navIdx: number;
  account: string;
  pw: string;
  tel: string;
  varify: string;
}

const VARIFY_TIME = 1000 * 45;

export default class Login extends React.PureComponent<LoginProps, LoginState> {
  constructor(props: LoginProps) {
    super(props);
    this.state = {
      navIdx: 0,
      account: '',
      pw: '',
      tel: '',
      varify: '',
    };
  }

  @autobind
  handleLoginClick() {
    const { navIdx, tel, varify,
      account, pw,
    } = this.state;
    if (navIdx) {
      if (!isTel(tel)) {
        openToast('手机号格式错误');
        return;
      }
      if (!isVarify(varify)) {
        openToast('验证码格式错误');
        return;
      }
      this.handleLogin();
    }
    if (!navIdx) {
      if (!isEmail(account)) {
        openToast('邮箱格式错误');
        return;
      }
      if (!isPW(pw)) {
        openToast('密码格式错误');
        return;
      }
      this.handleLogin();
    }
  }

  @autobind
  async handleLogin() {
    const { navIdx, account, varify, tel, pw } = this.state;
    const result = await loginApi({
      account: navIdx ? tel : account,
      password: navIdx ? varify : pw,
      type: navIdx ? LoginType.VERIFICATION : LoginType.PASSWORD,
    });
    if (result) {
      history.push('./MainAssets');
    } else {
      openToast('密码错误');
    }
  }

  @autobind
  handleRegisterClick() {
    const { handlePageChange } = this.props;
    handlePageChange(PageName.REGISTER);
  }

  @autobind
  handleNavClick(navIdx: number) {
    return () => {
      this.setState({
        navIdx,
      });
    };
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
  handlePwChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        pw: e.target.value,
      });
    }
  }

  @autobind
  handleTelChange(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target) {
      this.setState({
        tel: e.target.value,
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
  handleVarifyClick() {
    const { loginStore, onVarifyClick, currentTime } = this.props;
    const { varifyTime } = loginStore;
    if (varifyTime < currentTime) {
      const { tel } = this.state;
      if (!isTel(tel)) {
        openToast('手机格式错误');
        return;
      }

      sendVerificationApi({
        tel,
        type: VerificationType.LOGIN,
      });

      onVarifyClick(new Date().valueOf() + VARIFY_TIME);
    }
  }

  renderAccountInput() {
    const { account, pw } = this.state;

    return <div className="login-inputs">
      <div className="login-account">
        <input
          value={account}
          placeholder={i18n('邮箱')}
          onChange={this.handleAccountChange}
        />
      </div>
      <div className="login-pw">
        <input
          value={pw}
          placeholder={i18n('密码')}
          type="password"
          onChange={this.handlePwChange}
        />
      </div>
    </div>;
  }

  renderVarifyInput() {
    const { varify, tel } = this.state;

    return <div className="login-inputs">
      <div className="login-account">
        <input
          value={tel}
          placeholder={i18n('手机号')}
          onChange={this.handleTelChange}
        />
      </div>
      <div className="login-varifyLine">
        <div className="login-varify">
          <input
            value={varify}
            placeholder={i18n('验证码')}
            onChange={this.handleVarifyChange}
          />
        </div>
        <div className="login-varifyButton">
          {this.renderVarifyCode()}
        </div>
      </div>
    </div>;
  }

  renderVarifyCode() {
    const { currentTime, loginStore } = this.props;
    const { varifyTime } = loginStore;
    const text = !varifyTime ? i18n('获取验证码') : i18n('重新获取');

    if (!varifyTime || varifyTime - currentTime < 0) {
      return (
        <div
          className="login-varigyCode"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="login-varigyCode">
          <span>{i18n('已发送:')}</span>
          <span>{second}</span>
          <span>{i18n('s')}</span>
        </div>
      );
    }
  }

  render() {
    const { navIdx } = this.state;
    return (
      <div className="login-wrapper">
        <div className="login-header">{i18n('登录')}</div>
        <div className="login-kinds">
          <span
            className={cx({
              'login-kind': true,
              'login-kind_active': navIdx === 0,
            })}
            onClick={this.handleNavClick(0)}
          >
            {i18n('账号登录')}
          </span>
          <span
            className={cx({
              'login-kind': true,
              'login-kind_active': navIdx === 1,
            })}
            onClick={this.handleNavClick(1)}
          >
            {i18n('手机号登录')}
          </span>
        </div>
        <div className="login-content">
          {navIdx === 0 && this.renderAccountInput()}
          {navIdx === 1 && this.renderVarifyInput()}
        </div>
        <div className="login-button" onClick={this.handleLoginClick}>{i18n('登录')}</div>
        <div className="login-extraInfo">
          <div>{i18n('第三方登录')}</div>
          <div>
            {i18n('没有账号？')}
            <span
              className="login-toRegister" onClick={this.handleRegisterClick}
            >{i18n('注册')}</span>
          </div>
        </div>
      </div>
    );
  }
}
