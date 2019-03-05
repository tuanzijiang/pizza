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

interface SetPWProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
  onVarifyClick: (varifyTime: number) => void;
  varifyTime: number;
  currentTime: number;
}

interface SetPWState {
  tel: string;
}

const VARIFY_TIME = 1000 * 45;

export default class SetPW extends React.PureComponent<SetPWProps, SetPWState> {
  private varifyTimer: any = null;

  constructor(props: SetPWProps) {
    super(props);
    this.state = {
      tel: '',
    };
  }

  @autobind
  componentDidEnter(...extraInfo: any[]) {
    const { varifyTime } = this.props;
    const tel = extraInfo[0];
    this.setState({
      tel,
    });

    if (!varifyTime) {
      this.handleVarifyClick();
    }
  }

  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.FindPW, OpenType.LEFT);
  }

  renderMiddle() {
    return <span className="setPW-middle">{i18n('重设密码')}</span>;
  }

  @autobind
  handleVarifyClick() {
    const { varifyTime, onVarifyClick, currentTime } = this.props;
    if (varifyTime < currentTime) {
      onVarifyClick(new Date().valueOf() + VARIFY_TIME);
    }
  }

  renderVarifyCode() {
    const { varifyTime, currentTime  } = this.props;
    const text = !varifyTime ? i18n('获取验证码') : i18n('重新获取');

    if (!varifyTime || varifyTime - currentTime < 0) {
      return (
        <div
          className="setPW-varigy_ready"
          onClick={this.handleVarifyClick}
        >
          {text}
        </div>
      );
    } else {
      const { second } = timerFormater(varifyTime - currentTime);
      return (
        <div className="setPW-varigy_wait">
          <span>{i18n('已发送:')}</span>
          <span>{second}</span>
          <span>{i18n('s')}</span>
        </div>
      );
    }
  }

  render() {
    const setPWContent = cx({
      'setPW-content': true,
      'setPW-content_status': neetStatusBar,
    });
    const { tel } = this.state;

    return (
      <div className="setPW-wrapper">
        <Banner
          right={null}
          middle={this.renderMiddle()}
          leftClick={this.handleLeftClick}
        />
        <form className={setPWContent}>
          <div className="setPW-tel">
            {i18n('验证码发送至:')}
            <span>{tel}</span>
          </div>
          <div className="setPW-input">
            <Input placeholde={i18n('验证码')} type="number" right={this.renderVarifyCode()} />
          </div>
          <div className="setPW-input">
            <Input placeholde={i18n('新密码')} type="password" />
          </div>
          <div className="setPW-button">{i18n('确定')}</div>
        </form>
      </div>
    );
  }
}
