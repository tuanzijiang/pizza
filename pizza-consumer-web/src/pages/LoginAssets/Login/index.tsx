import * as React from 'react';
import './index.scss';
import Banner from '@biz-components/Banner';
import { OpenType } from '@biz-components/PageAssets';
import t from '@utils/i18n';
import { LoginAssetName } from '../index';
import autobind from 'autobind-decorator';

interface LoginProps {
  onPageChange(idx: LoginAssetName, openType?: OpenType): void;
}

interface LoginState { }

export default class Login extends React.PureComponent<LoginProps, LoginState> {
  @autobind
  handleRightClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.LoginPW);
  }

  renderMiddle() {
    return <span className="login-middle">{t('登录')}</span>;
  }

  renderRight() {
    return <span className="login-right">{t('密码登录')}</span>;
  }

  render() {
    return (
      <div className="login-wrapper">
        <Banner
          left={null}
          right={this.renderRight()}
          middle={this.renderMiddle()}
          rightClick={this.handleRightClick}
        />
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
