import * as React from 'react';
import PageAssets, { OpenType } from '@biz-components/PageAssets';
import './index.scss';
import Login from './Login';
import LoginPW from './LoginPW';
import autobind from 'autobind-decorator';

interface LoginAssetsProps { }

interface LoginAssetsState { }

export enum LoginAssetName {
  Login = 'Login',
  LoginPW = 'LoginPW',
}

const config = {
  [LoginAssetName.Login]: 0,
  [LoginAssetName.LoginPW]: 1,
};

export default class LoginAssets extends React.PureComponent<LoginAssetsProps, LoginAssetsState> {
  private pageAssetsEl: React.RefObject<PageAssets> = React.createRef();

  @autobind
  handlePageChange(name: LoginAssetName, openType?: OpenType) {
    if (this.pageAssetsEl && this.pageAssetsEl.current) {
      this.pageAssetsEl.current.openByName(config[name], openType);
    }
  }

  render() {
    return (
      <div className="loginAssets">
        <PageAssets ref={this.pageAssetsEl}>
          <Login onPageChange={this.handlePageChange}/>
          <LoginPW onPageChange={this.handlePageChange}/>
          <div>2</div>
        </PageAssets>
      </div>
    );
  }
}

// export default connect((state) => {
//   return {
//     page: state.page.edit,
//     entity: state.entity,
//   };
// }, (dispatch) => {
//   return {
//     updateNavIdx: (idx: number) => { dispatch(pageActionCreator.edit.updateNavIdx(idx)); },
//     updateDisplayId: (displayId: string) => {
//       dispatch(entityActionCreator.display.updateDisplayId(displayId));
//     },
//   };
// })(Edit);
