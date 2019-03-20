import * as React from 'react';
import './index.scss';
import { connect } from 'react-redux';
import cx from 'classnames';
import i18n from '@src/utils/i18n';
import Icon from '@biz-components/Icon';
import { fetchUserApi } from '@src/services/api-fetch-user';
import autobind from 'autobind-decorator';

export enum PageName {
  MENU = 1,
  ORDER = 2,
}

interface MenuProps {
  entityStore: any;
}

interface MenuState {
}

export default class Menu extends React.PureComponent<MenuProps, MenuState> {
  componentDidMount() {

  }

  render() {
    const { entityStore } = this.props;
    const { menu } = entityStore;
    console.warn(1111, menu);

    return (
      <div className="menu-wrapper">
        {
          menu &&
          <>
            <div className="menu-subMenu">
            </div>
            <div className="menu-pizzaItems">
            </div>
          </>
        }
      </div>
    );
  }
}
