import * as React from 'react';
import { Icon } from '@biz-components/Icon';
import i18n from '@utils/i18n';
import { page as pageActionCreator, entity as entityActionCreator } from '@store/action';
import autobind from 'autobind-decorator';
import { connect } from 'react-redux';
import cx from 'classnames';
import {
  ManageSubPage,
} from '@schemas/page';
import {
  StoreState, EntityState,
} from '@schemas/store';
import './index.less';
import { EditPageState } from '@src/store/reducers/page/schema';
import { Button } from 'antd';
import Template from './Template';
import Display from './Display';
import Config from './Config';

const items: Item[] = [{
  type: 'icon-page-tempalte',
  subpage: ManageSubPage.Activity,
  name: i18n('页面模版'),
  getComponent: () => <Template idx={0} />,
  props: {},
}, {
  type: 'icon-goods-template',
  subpage: ManageSubPage.Activity,
  name: i18n('商品模版'),
  getComponent: () => <Template idx={1} />,
  props: {},
}, {
  type: 'icon-marketing-template',
  subpage: ManageSubPage.Goods,
  name: i18n('营销模版'),
  getComponent: () => <Template idx={2} />,
  props: {},
}];

interface Item {
  type: string;
  subpage: ManageSubPage;
  name: string;
  getComponent(props: object): React.ReactNode;
  props: Object;
}

interface ManagerProps {
  page: EditPageState;
  entity: EntityState;
  updateNavIdx(idx: number): void;
  updateDisplayId(displayId: string): void;
}

class Edit extends React.PureComponent<ManagerProps, {}> {

  constructor(props: ManagerProps) {
    super(props);
    const {
      // updateActivities, updateGoods, updateGoodsGroup,
    } = this.props;
    items.forEach((item) => {
      const { subpage } = item;
      if (subpage === ManageSubPage.Activity) {
        item.props = {
        };
      } else if (subpage === ManageSubPage.Goods) {
        item.props = {
        };
      }
    });
  }

  @autobind
  handleNavItemClick(idx: number) {
    return () => {
      const { updateNavIdx } = this.props;
      updateNavIdx(idx);
    };
  }

  render() {
    const { page, entity, updateDisplayId } = this.props;
    const { display } = entity;
    const navItems = items.map((item, key) => (
      <div
        className={cx({
          'edit-navItem': true,
          'edit-navItem_active': key === page.navIdx,
        })}
        key={key}
        onClick={this.handleNavItemClick(key)}
      >
        <div className="edit-navItemIcon">
          <Icon type={item.type} />
        </div>
        <div className="edit-navItemName">
          {item.name}
        </div>
      </div>
    ));

    const editMainContent = items.map(({ getComponent, props }, key) => (
      key === page.navIdx &&
      <div className="edit-mainContent" key={key}>
        {
          getComponent({
            entity,
            ...props,
          })
        }
      </div>
    ));

    return (
      <div className="edit-wrapper">
        <div className="edit-nav">
          <div className="edit-logo">puzzle</div>
          {navItems}
        </div>
        <div className="edit-main">
          <div className="edit-mainLeft">
            {editMainContent}
          </div>
          <div className="edit-mainRight">
            <div className="edit-mainHeader">
              <Button type="primary" className="edit-saveButton">{i18n('保存')}</Button>
              <Button className="edit-releaseButton">{i18n('发布')}</Button>
              <Button className="edit-previewButton">{i18n('预览')}</Button>
            </div>
            <div className="edit-mainContent">
              <div className="edit-mainCanvas">
                <Display
                  displayInfo={display}
                  updateDisplayId={updateDisplayId}
                />
              </div>
              <div className="edit-mainSideBar">
                <Config 
                  displayInfo={display}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default connect((state: StoreState) => {
  return {
    page: state.page.edit,
    entity: state.entity,
  };
}, (dispatch) => {
  return {
    updateNavIdx: (idx: number) => { dispatch(pageActionCreator.edit.updateNavIdx(idx)); },
    updateDisplayId: (displayId: string) => {
      dispatch(entityActionCreator.display.updateDisplayId(displayId));
    },
  };
})(Edit);
