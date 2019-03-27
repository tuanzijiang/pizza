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

interface BindTelProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
  onVarifyClick: (varifyTime: number) => void;
  varifyTime: number;
  currentTime: number;
}

interface BindTelState {
  account: string;
  varify: string;
}

const VARIFY_TIME = 1000 * 45;

export default class BindTel extends React.PureComponent<BindTelProps, BindTelState> {
  private varifyTimer: any = null;

  constructor(props: BindTelProps) {
    super(props);
    this.state = {
      account: '',
      varify: '',
    };
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.RegisterEmail, OpenType.LEFT);
  }

  @autobind
  componentDidEnter(...extraInfo: any[]) {
    // const { varifyTime } = this.props;
    // const tel = extraInfo[0];
    // this.setState({
    //   tel,
    // });

    // if (!varifyTime) {
    //   this.handleVarifyClick();
    // }
  }

  renderMiddle() {
    return <span className="bindTel-middle">{i18n('绑定手机号')}</span>;
  }

  @autobind
  handleVarifyClick() {
    const { currentTime , varifyTime, onVarifyClick } = this.props;
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

  renderVarifyCode() {
    const { varifyTime, currentTime } = this.props;
    const text = !varifyTime ? i18n('获取验证码') : i18n('重新获取');

    if (!varifyTime || varifyTime - currentTime < 0) {
      return (
        <div
          className="bindTel-varigy_ready"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="bindTel-varigy_wait">
          <span>{i18n('已发送:')}</span>
          <span>{second}</span>
          <span>{i18n('s')}</span>
        </div>
      );
    }
  }

  render() {
    const bindTelContent = cx({
      'bindTel-content': true,
      'bindTel-content_status': neetStatusBar,
    });
    const { varify, account } = this.state;

    return (
      <div className="bindTel-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <div className={bindTelContent}>
          <div className="bindTel-beforeInput" />
          <div className="bindTel-input">
            <Input
              placeholde={i18n('手机号')}
              right={this.renderVarifyCode()}
              value={account}
              onChange={this.handleAccountChange}
            />
          </div>
          <div className="bindTel-input">
            <Input
              placeholde={i18n('验证码')}
              type="number"
              value={varify}
              onChange={this.handleVarifyChange}
            />
          </div>
          <div className="bindTel-button">{i18n('绑定')}</div>
        </div>
      </div>
    );
  }
}
