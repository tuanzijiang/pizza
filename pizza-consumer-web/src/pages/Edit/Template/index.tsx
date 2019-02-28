import * as React from 'react';
import { Tag, TagBehavior } from '@biz-components/Tag/schema';
import TagComponent, { TagProps } from '@biz-components/Tag';
import pageConfig from './page.config';
import './index.less';
import autobind from 'autobind-decorator';

export interface TemplateProps {
  idx: number;
}

export interface TemplateState { }

interface ConfigItem {
  group_name: string;
  group_items: Tag[];
}

const configs: ConfigItem[][] = [
  pageConfig,
  [],
  [],
];

export default class Template extends React.PureComponent<TemplateProps, TemplateState> {

  @autobind
  handleItemClick() {

  }

  render() {
    const { idx } = this.props;
    const config = configs[idx];

    return <div className="template-wrapper">
      {config.map(({ group_items, group_name }, key) => (
        <div
          className="template-group"
          key={key}
        >
          <div className="template-groupName">{group_name}</div>
          <div
            className="template-groupItems"
            onClick={this.handleItemClick}
          >
            {
              group_items.map((item: TagProps, key) => (
                <div className="template-groupItem" key={key}>
                  <TagComponent {...item} behavior={TagBehavior.SIDEBAR} />
                </div>
              ))
            }
          </div>
        </div>
      ))}
    </div>;
  }
}
