import * as React from 'react';
import { Input, Button, Table, Divider } from 'antd';
import { ColumnProps } from 'antd/lib/table';
import {
  TIMEFOAMTER,
} from '@src/schemas/utils';
import {
  EntityState, PageEntity, ActivityEntity,
  ActivityStateEntity,
} from '@schemas//store';
import './index.less';
import i18n from '@src/utils/i18n';
import { fetchActivity } from '@net/activity';
import { formater } from '@utils/time';

const Search = Input.Search;

const columns: ColumnProps<ActivityEntity>[] = [{
  key: 'activity_id',
  title: i18n('活动ID'),
  dataIndex: 'activity_id',
  width: 200,
  render: (activity_id) =>
    <div className="activity-item_itemID activity-item">{activity_id}</div>,
}, {
  key: 'activity_name',
  title: i18n('活动名称'),
  dataIndex: 'activity_name',
  width: 200,
  render: (activity_name) =>
    <div className="activity-item_itemName activity-item">{activity_name}</div>,
}, {
  key: 'creator_name',
  title: i18n('创建人'),
  dataIndex: 'creator_name',
  width: 130,
  render: (creator_name) =>
    <div className="activity-item_itemCreator activity-item">{creator_name}</div>,
}, {
  key: 'time',
  title: i18n('有效期'),
  dataIndex: 'time',
  width: 220,
  render: (time) =>
    <div className="activity-item_itemTime activity-item">
      <div className="activity-item_itemStart activity-item">
        始:{formater(time.start, TIMEFOAMTER.TYPE1)}
      </div>
      <div className="activity-item_itemEnd activity-item">
        止:{formater(time.start, TIMEFOAMTER.TYPE1)}
      </div>
    </div>,
}, {
  key: 'state',
  title: i18n('当前状态'),
  dataIndex: 'state',
  width: 100,
  render: (state) =>
    <div className="activity-item_itemState activity-item">
      {
        state === ActivityStateEntity.INVALID &&
        <span className="activity-itemState_invalid">{i18n('已失效')}</span>
      }
      {
        state === ActivityStateEntity.START &&
        <span className="activity-itemState_start">{i18n('已开始')}</span>
      }
      {
        state === ActivityStateEntity.TO_START &&
        <span className="activity-itemState_toStart">{i18n('未开始')}</span>
      }
      {
        state === ActivityStateEntity.STOP &&
        <span className="activity-itemState_stop">{i18n('已结束')}</span>
      }
    </div>,
}, {
  key: 'operation',
  title: i18n('操作'),
  dataIndex: 'operation',
  render: () =>
    <div className="activity-item">
      <a href="javascript:;">{i18n('新增页面')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('投放')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('转有效')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('编辑')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('删除')}</a>
    </div>,
}];

const columnInner: ColumnProps<PageEntity>[] = [{
  key: 'page_id',
  title: i18n('页面ID'),
  dataIndex: 'page_id',
  width: 200,
  render: (activity_id) =>
    <div className="activity-item_itemID activity-item">{activity_id}</div>,
}, {
  key: 'page_name',
  title: i18n('页面名称'),
  dataIndex: 'page_name',
  width: 200,
  render: (activity_name) =>
    <div className="activity-item_itemName activity-item">{activity_name}</div>,
}, {
  key: 'creator_name',
  title: i18n('创建人'),
  dataIndex: 'creator_name',
  width: 130,
  render: (creator_name) =>
    <div className="activity-item_itemCreator activity-item">{creator_name}</div>,
}, {
  key: 'time',
  title: i18n('有效期'),
  dataIndex: 'time',
  width: 220,
  render: (time) =>
    <div className="activity-item_itemTime activity-item">
      <div className="activity-item_itemStart activity-item">
        始:{formater(time.start, TIMEFOAMTER.TYPE1)}
      </div>
      <div className="activity-item_itemEnd activity-item">
        止:{formater(time.start, TIMEFOAMTER.TYPE1)}
      </div>
    </div>,
}, {
  key: 'state',
  title: i18n('当前状态'),
  dataIndex: 'state',
  width: 100,
  render: (state) =>
    <div className="activity-item_itemState activity-item">
      {
        state === ActivityStateEntity.INVALID &&
        <span className="activity-itemState_invalid">{i18n('已失效')}</span>
      }
      {
        state === ActivityStateEntity.START &&
        <span className="activity-itemState_start">{i18n('已开始')}</span>
      }
      {
        state === ActivityStateEntity.TO_START &&
        <span className="activity-itemState_toStart">{i18n('未开始')}</span>
      }
      {
        state === ActivityStateEntity.STOP &&
        <span className="activity-itemState_stop">{i18n('已结束')}</span>
      }
    </div>,
}, {
  key: 'operation',
  title: i18n('操作'),
  dataIndex: 'operation',
  render: () =>
    <div className="activity-item">
      <a href="javascript:;">{i18n('配置页面')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('用户群')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('编辑')}</a>
      <Divider type="vertical" />
      <a href="javascript:;">{i18n('删除')}</a>
    </div>,
}];

const expandedRowRender = (record: ActivityEntity) =>
  <Table columns={columnInner} dataSource={record.pages} pagination={false} showHeader={false} />;

export interface ActivityState {
  keyword: string;
  pageIdx: number;
}

export interface ActivityProps {
  entity: EntityState;
  updateActivities(activities: ActivityEntity[]): void;
}

export default class Activity extends React.PureComponent<ActivityProps, ActivityState> {
  constructor(props: ActivityProps) {
    super(props);
    this.state = {
      keyword: '',
      pageIdx: 1,
    };
  }

  componentDidMount() {
    this.init();
  }

  async init() {
    const { keyword, pageIdx } = this.state;
    const { updateActivities } = this.props;
    try {
      const result = await fetchActivity({
        keyword,
        pageIdx,
      });
      updateActivities(result.response);
    } catch (e) { }
  }

  render() {
    const { entity } = this.props;
    const { activity } = entity;
    const { activities } = activity;

    return (
      <div className="activity-wrapper">
        <div className="activity-header">
          <div className="activity-headerLeft">
            <div className="activity-headerSearch">
              <Search enterButton placeholder={i18n('搜索项目ID，项目名称，创建人')} />
            </div>
          </div>
          <div className="activity-headerRight">
            <Button type={'primary'}>{i18n('新增活动')}</Button>
          </div>
        </div>
        <div className="activity-main">
          <div className="activity-mainContent">
            <div className="activity-table">
              <Table
                dataSource={activities}
                columns={columns}
                pagination={false}
                expandedRowRender={expandedRowRender}
                scroll={{ y: 100 }}
                rowKey={record => record.activity_id}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
