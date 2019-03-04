import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Login from './Login';
import LoginPW from './LoginPW';
import FindPW from './FindPW';
import SetPW from './SetPW';
import { connect } from 'react-redux';
import { page as pageActionCreator } from '@store/action';
import autobind from 'autobind-decorator';

interface LoginAssetsProps {
  loginStore: any;
  updateVarifyTime: (varifyTime: number) => void;
  updateSetVarifyTime: (varifyTime: number) => void;
}

interface LoginAssetsState { }

export enum LoginAssetName {
  Login = 'Login',
  LoginPW = 'LoginPW',
  FindPW = 'FindPW',
  SetPW = 'SetPW',
}

const config = {
  [LoginAssetName.Login]: 0,
  [LoginAssetName.LoginPW]: 1,
  [LoginAssetName.FindPW]: 2,
  [LoginAssetName.SetPW]: 3,
};

export class LoginAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();
  private setPWEl: React.RefObject<SetPW> = React.createRef();

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

  render() {
    const {
      loginStore, updateVarifyTime, updateSetVarifyTime,
    } = this.props;
    const { varifyTime, setVarifyTime } = loginStore;
    return (
      <div className="loginAssets">
        <PageAssets ref={this.pageAssetsEl} changeCb={this.onPageChange}>
          <Login
            onPageChange={this.handlePageChange}
            onVarifyClick={updateVarifyTime}
            varifyTime={varifyTime}
          />
          <LoginPW onPageChange={this.handlePageChange} />
          <FindPW onPageChange={this.handlePageChange} />
          <SetPW
            ref={this.setPWEl}
            onPageChange={this.handlePageChange}
            onVarifyClick={updateSetVarifyTime}
            varifyTime={setVarifyTime}
          />
        </PageAssets>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    loginStore: state.page.login,
  };
}, (dispatch) => {
  return {
    updateVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateVarifyTime(varifyTime));
    },
    updateSetVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateSetVarifyTime(varifyTime));
    },
  };
})(LoginAssets);
