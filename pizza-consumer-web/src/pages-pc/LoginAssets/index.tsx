import * as React from 'react';
import './index.scss';
import { connect } from 'react-redux';
import { pagePc as pageActionCreator } from '@store/action';
import BlurBg from '@biz-components/BlurBg';
import Login from './Login';

interface LoginAssetsProps {
  loginStore: any;
  entityStore: any;
  updateVarifyTime: (varifyTime: number) => void;
}

interface LoginAssetsState {
  currentTime: number;
}

export class LoginAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private varifyTimer: any = null;
  constructor(props: LoginAssetsProps) {
    super(props);
    this.state = {
      currentTime: new Date().valueOf(),
    };
  }

  componentDidMount() {
    this.varifyTimer = setInterval(() => {
      this.setState({
        currentTime: new Date().valueOf(),
      });
    }, 1000);
  }

  componentWillUnmount() {
    clearInterval(this.varifyTimer);
  }

  render() {
    const { currentTime } = this.state;
    const { loginStore, updateVarifyTime } = this.props;
    return (
      <div className="loginAssets">
        <div className="loginAssets-wrapper">
          <BlurBg />
          <div className="loginAssets-main">
            <div className="loginAssets-slogan">
              <div className="loginAssets-header">Pizza Express</div>
              <div className="loginAssets-info">Pizza Express将为您提供30分钟必达配送服务</div>
            </div>
            <div className="loginAssets-box">
              <Login
                currentTime={currentTime} loginStore={loginStore} onVarifyClick={updateVarifyTime}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default connect((state: any) => {
  return {
    loginStore: state.pagePc.login,
    entityStore: state.entity,
  };
}, (dispatch) => {
  return {
    updateVarifyTime: (varifyTime: number) => {
      dispatch(pageActionCreator.login.updateVarifyTime(varifyTime));
    },
  };
})(LoginAssets);
