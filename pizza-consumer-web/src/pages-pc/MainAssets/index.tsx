import * as React from 'react';
import './index.scss';
import { connect } from 'react-redux';
import { pageMobile as pageActionCreator } from '@store/action';

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

export class LoginAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  constructor(props: LoginAssetsProps) {
    super(props);
  }

  render() {
    return (
      <div className="loginAssets">
        <div className="login">123 login</div>
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
