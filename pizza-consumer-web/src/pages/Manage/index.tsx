import * as React from 'react';
import { Icon } from '@biz-components/Icon';
import i18n from '@utils/i18n';
import { page as pageActionCreator, entity as entityActionCreator } from '@store/action';
import autobind from 'autobind-decorator';
import { connect } from 'react-redux';
import cx from 'classnames';
import Activity, { ActivityProps } from './Activity';
import Goods, { GoodsProps } from './Goods';
import {
  ManageSubPage,
} from '@schemas/page';
import {
  StoreState, ManagePageState, EntityState,
  ActivityEntity,
} from '@schemas/store';
import './index.less';
import { GoodsEntity, GoodsGroupEntity } from '@src/store/reducers/entity/schema';

const items: Item[] = [{
  type: 'icon-activity-manager',
  subpage: ManageSubPage.Activity,
  name: i18n('活动管理'),
  getComponent: (props: ActivityProps) => <Activity {...props} />,
  props: {},
}, {
  type: 'icon-user-manager',
  subpage: ManageSubPage.Activity,
  name: i18n('用户管理'),
  getComponent: (props: ActivityProps) => <Activity {...props} />,
  props: {},
}, {
  type: 'icon-goods-manager',
  subpage: ManageSubPage.Goods,
  name: i18n('商品管理'),
  getComponent: (props: GoodsProps) => <Goods {...props} />,
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
  page: ManagePageState;
  updateNavIdx(idx: number): void;
  updateActivities(activiies: ActivityEntity[]): void;
  updateGoods(goods: GoodsEntity[]): void;
  updateGoodsGroup(group: GoodsGroupEntity[]): void;
  entity: EntityState;
}

class Manager extends React.PureComponent<ManagerProps, {}> {

  constructor(props: ManagerProps) {
    super(props);
    const {
      updateActivities, updateGoods, updateGoodsGroup,
    } = this.props;
    items.forEach((item) => {
      const { subpage } = item;
      if (subpage === ManageSubPage.Activity) {
        item.props = {
          updateActivities,
        };
      } else if (subpage === ManageSubPage.Goods) {
        item.props = {
          updateGoods,
          updateGoodsGroup,
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
    const { page, entity } = this.props;
    const navItems = items.map((item, key) => (
      <div
        className={cx({
          'manager-navItem': true,
          'manager-navItem_active': key === page.navIdx,
        })}
        key={key}
        onClick={this.handleNavItemClick(key)}
      >
        <div className="manager-navItemIcon">
          <Icon type={item.type} />
        </div>
        <div className="manager-navItemName">
          {item.name}
        </div>
      </div>
    ));

    const managerMainContent = items.map(({ getComponent, props }, key) => (
      key === page.navIdx &&
      <div className="manager-mainContent" key={key}>
        {
          getComponent({
            entity,
            ...props,
          })
        }
      </div>
    ));

    return (
      <div className="manager-wrapper">
        <div className="manager-nav">
          <div className="manager-logo">puzzle</div>
          {navItems}
        </div>
        <div className="manager-main">
          {managerMainContent}
        </div>
      </div>
    );
  }
}

export default connect((state: StoreState) => {
  return {
    page: state.page.manage,
    entity: state.entity,
  };
}, (dispatch) => {
  return {
    updateNavIdx: (idx: number) => { dispatch(pageActionCreator.manage.updateNavIdx(idx)); },
    updateActivities: (activiies: ActivityEntity[]) => {
      dispatch(entityActionCreator.activity.updateActivities(activiies));
    },
    updateGoods: (goods: GoodsEntity[]) => {
      dispatch(entityActionCreator.goods.updateGoods(goods));
    },
    updateGoodsGroup: (group: GoodsGroupEntity[]) => {
      dispatch(entityActionCreator.goods.updateGoodGroup(group));
    },
  };
})(Manager);
