import * as React from 'react';
import './index.scss';
import Icon from '@biz-components/Icon';
import { fetchUserApi } from '@src/services/api-fetch-user';
import { User } from '@src/entity';
import i18n from '@src/utils/i18n';
import autobind from 'autobind-decorator';
import history from '@utils/history';

interface MeProps {
  user: User;
}

interface MeState { }

export default class Me extends React.PureComponent<MeProps, MeState> {
  componentDidMount() {
    fetchUserApi({
      userId: 123,
    });
  }

  @autobind
  handleLogoutClick(e: React.MouseEvent) {
    history.push('./LoginAssets');
  }

  render() {
    const { user } = this.props;
    const { phone, name } = user;
    return (
      <div className="me-wrapper">
        <div className="me-header">
          <div className="me-avatar">
            <Icon name="me" classnames="me-avatar_default" />
          </div>
          <div className="me-info">
            <div className="me-name">
              {name}
            </div>
            <div className="me-phone">
              <Icon name="phone" classnames="me-iconPhone" />
              {phone}
            </div>
          </div>
        </div>
        <div className="me-content">
          <div className="me-contentItem">
            <div className="me-contentItemTitle">
              {i18n('用户名')}
            </div>
            <div className="me-contentItemInfo">
              {name}
            </div>
            <Icon name="right" classnames="me-iconRight" />
          </div>
          <div className="me-contentItem">
            <div className="me-contentItemTitle">
              {i18n('收获地址')}
            </div>
            <div className="me-contentItemInfo" />
            <Icon name="right" classnames="me-iconRight" />
          </div>
          <div className="me-contentItem">
            <div className="me-contentItemTitle">
              {i18n('常住地址')}
            </div>
            <div className="me-contentItemInfo" />
            <Icon name="right" classnames="me-iconRight" />
          </div>
          <div className="me-contentItem">
            <div className="me-contentItemTitle">
              {i18n('生日')}
            </div>
            <div className="me-contentItemInfo" />
            <Icon name="right" classnames="me-iconRight" />
          </div>
          <div
            className="me-logout"
            onClick={this.handleLogoutClick}
          >
            {i18n('退出登录')}
          </div>
        </div>
      </div>
    );
  }
}
