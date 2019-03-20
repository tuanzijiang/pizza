import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import i18n from '@utils/i18n';
import { LoginAssetName } from '../index';
import Input from '@biz-components/Input';
import autobind from 'autobind-decorator';

interface RegisterEmailProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
}

interface RegisterEmailState {
  account: string;
  password: string;
}

export default class RegisterEmail extends
  React.PureComponent<RegisterEmailProps, RegisterEmailState> {
  constructor(props: RegisterEmailProps) {
    super(props);
    this.state = {
      account: '',
      password: '',
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.Register, OpenType.LEFT);
  }

  @autobind
  handleBindPWClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.BindTel, OpenType.RIGHT);
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

  renderMiddle() {
    return <span className="registerEmail-middle">{i18n('密码登录')}</span>;
  }

  render() {
    const { account, password } = this.state;
    return (
      <div className="registerEmail-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className="registerEmail-content">
          <div className="registerEmail-beforeInput" />
          <div className="registerEmail-input">
            <Input
              placeholde={i18n('手机号/邮箱')}
              onChange={this.handleAccountChange}
              value={account}
            />
          </div>
          <form className="registerEmail-input">
            <Input
              placeholde={i18n('密码')}
              type="password"
              onChange={this.handlePasswordChange}
              value={password}
            />
          </form>
          <div className="registerEmail-button" onClick={this.handleBindPWClick}>{i18n('注册')}</div>
        </div>
      </div>
    );
  }
}
