import * as React from 'react';
import './index.scss';
import cx from 'classnames';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import { timerFormater } from '@utils/time';
import { PageName } from '..';

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
    const { handlePageChange } = this.props;
    handlePageChange(PageName.LOGIN);
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
      onVarifyClick(new Date().valueOf() + VARIFY_TIME);
    }
  }

  renderAccountInput() {
    const { account, pw } = this.state;

    return <div className="register-inputs">
      <div className="register-account">
        <input
          value={account}
          placeholder={i18n('邮箱')}
          onChange={this.handleAccountChange}
        />
      </div>
      <div className="register-pw">
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

    return <div className="register-inputs">
      <div className="register-account">
        <input
          value={tel}
          placeholder={i18n('手机号')}
          onChange={this.handleTelChange}
        />
      </div>
      <div className="register-varifyLine">
        <div className="register-varify">
          <input
            value={varify}
            placeholder={i18n('验证码')}
            onChange={this.handleVarifyChange}
          />
        </div>
        <div className="register-varifyButton">
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
          className="register-varigyCode"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="register-varigyCode">
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
      <div className="register-wrapper">
        <div className="register-header">{i18n('注册')}</div>
        <div className="register-kinds">
          <span
            className={cx({
              'register-kind': true,
              'register-kind_active': navIdx === 0,
            })}
            onClick={this.handleNavClick(0)}
          >
            {i18n('邮箱注册')}
          </span>
          <span
            className={cx({
              'register-kind': true,
              'register-kind_active': navIdx === 1,
            })}
            onClick={this.handleNavClick(1)}
          >
            {i18n('手机注册')}
          </span>
        </div>
        <div className="register-content">
          {navIdx === 0 && this.renderAccountInput()}
          {navIdx === 1 && this.renderVarifyInput()}
        </div>
        <div className="register-button">{i18n('注册')}</div>
        <div className="register-extraInfo">
          <div></div>
          <div>
            {i18n('已有账号？')}
            <span
              className="register-toRegister" onClick={this.handleLoginClick}
            >{i18n('登录')}</span>
          </div>
        </div>
      </div>
    );
  }
}
