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

export default class LoginPW extends React.PureComponent<LoginProps, LoginState> {
  @autobind
  handleLeftClick(e: React.MouseEvent<React.ReactNode>) {
    const { onPageChange } = this.props;
    onPageChange(LoginAssetName.Login, OpenType.RIGHT);
  }

  renderMiddle() {
    return <span className="login-middle">{t('密码登录')}</span>;
  }

  render() {
    return (
      <div className="login-wrapper">
        <Banner
          // right={null}
          middle={this.renderMiddle()}
          rightClick={this.handleLeftClick}
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
