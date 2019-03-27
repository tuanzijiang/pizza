import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Login from './Login';
import LoginPW from './LoginPW';
import FindPW from './FindPW';
import SetPW from './SetPW';
import Register from './Register';
import RegisterEmail from './RegisterEmail';
import BindTel from './BindTel';
import { connect } from 'react-redux';
import { pageMobile as pageActionCreator } from '@store/action';
import autobind from 'autobind-decorator';

interface LoginAssetsProps {
  loginStore: any;
  updateVarifyTime: (varifyTime: number) => void;
  updateSetVarifyTime: (varifyTime: number) => void;
  updateRegisterVarifyTime: (varifyTime: number) => void;
  updateBindVarifyTime: (varifyTime: number) => void;
}

interface LoginAssetsState {
  currentTime: number;
}

export enum LoginAssetName {
  Login = 0,
  LoginPW = 1,
  FindPW = 2,
  SetPW = 3,
  Register = 4,
  RegisterEmail = 5,
  BindTel = 6,
}

const config = {
  [LoginAssetName.Login]: 0,
  [LoginAssetName.LoginPW]: 1,
  [LoginAssetName.FindPW]: 2,
  [LoginAssetName.SetPW]: 3,
  [LoginAssetName.Register]: 4,
  [LoginAssetName.RegisterEmail]: 5,
  [LoginAssetName.BindTel]: 6,
};

const handleTouchMove = (e: TouchEvent) => {
  // e.preventDefault();
};

export class LoginAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();
  private setPWEl: React.RefObject<SetPW> = React.createRef();
  private loginAssetsEl: React.RefObject<HTMLDivElement> = React.createRef();
  private varifyTimer: any = null;

  constructor(props: LoginAssetsProps) {
    super(props);
    this.state = {
      currentTime: new Date().valueOf(),
    };
  }

  @autobind
  handlePageChange(name: LoginAssetName, openType?: OpenType, ...extraInfo: any[]) {
    if (this.pageAssetsEl && this.pageAssetsEl.current) {
      this.pageAssetsEl.current.openByName(config[name], openType, ...extraInfo);
    }
  }

  @autobind
  onPageChange(idx: number, ...extraInfo: any[]) {
    // 进入setPW页面需要立即发送验证码
    if (idx === config[LoginAssetName.SetPW] && this.setPWEl) {
      this.setPWEl.current.componentDidEnter(...extraInfo);
    }
  }

  componentDidMount() {
    this.loginAssetsEl.current.addEventListener('touchmove', handleTouchMove, { passive: false });
    this.varifyTimer = setInterval(() => {
      this.setState({
        currentTime: new Date().valueOf(),
      });
    }, 1000);
  }

  componentWillUnmount() {
    this.loginAssetsEl.current.removeEventListener('touchmove', handleTouchMove);
    clearInterval(this.varifyTimer);
  }
  render() {
    const {
      loginStore, updateVarifyTime, updateSetVarifyTime,
      updateBindVarifyTime, updateRegisterVarifyTime,
    } = this.props;
    const { currentTime } = this.state;
    const {
      varifyTime, setVarifyTime,
      registerVarifyTime, bindVarifyTime,
    } = loginStore;
    return (
      <div className="loginAssets" ref={this.loginAssetsEl}>
        <PageAssets
          init={LoginAssetName.Login} ref={this.pageAssetsEl} changeCb={this.onPageChange}
        >
          <Login
            onPageChange={this.handlePageChange}
            onVarifyClick={updateVarifyTime}
            varifyTime={varifyTime}
            currentTime={currentTime}
          />
          <LoginPW onPageChange={this.handlePageChange} />
          <FindPW onPageChange={this.handlePageChange} />
          <SetPW
            ref={this.setPWEl}
            onPageChange={this.handlePageChange}
            onVarifyClick={updateSetVarifyTime}
            varifyTime={setVarifyTime}
            currentTime={currentTime}
          />
          <Register
            onPageChange={this.handlePageChange}
            onVarifyClick={updateRegisterVarifyTime}
            varifyTime={registerVarifyTime}
            currentTime={currentTime}
          />
          <RegisterEmail onPageChange={this.handlePageChange} />
          <BindTel
            onPageChange={this.handlePageChange}
            onVarifyClick={updateBindVarifyTime}
            varifyTime={bindVarifyTime}
            currentTime={currentTime}
          />
        </PageAssets>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    loginStore: state.pageMobile.login,
  };
}, (dispatch) => {
  return {
    updateVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateVarifyTime(varifyTime));
    },
    updateSetVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateSetVarifyTime(varifyTime));
    },
    updateBindVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateBindVarifyTime(varifyTime));
    },
    updateRegisterVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateRegisterVarifyTime(varifyTime));
    },
  };
})(LoginAssets);
