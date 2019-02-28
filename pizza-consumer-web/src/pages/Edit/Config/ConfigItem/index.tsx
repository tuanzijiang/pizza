import * as React from 'react';
import './index.less';
import PropItem from '../PropItem';
import { DisplayEntity } from '@src/store/reducers/entity/display/schema';
import i18n from '@src/utils/i18n';

export interface ConfigItemProps {
  rootKey: string;
  idx: number;
  currTag: DisplayEntity;
}

export interface ConfigItemState { }

export default class ConfigItem extends React.PureComponent<ConfigItemProps, ConfigItemState> {
  render() {
    const { rootKey, currTag, idx } = this.props;
    const { tag_props } = currTag;
    const isRoot = rootKey === currTag.id;
    const tag_props_arr = Object.entries(tag_props);

    return (
      <div className="configItem-wrapper">
        <div className="configItem-content">
          <div className="configItem-name">
            {isRoot ? i18n('基础配置') : i18n(`元素${idx}`)}
          </div>
          <table className="configItem-props">
            <tbody>
              {tag_props_arr.map(([prop_name, prop_value], key) => (
                <PropItem
                  key={key}
                  rootKey={rootKey}
                  currTag={currTag}
                  propName={prop_name}
                  propValue={prop_value}
                />
              ))}
            </tbody>
          </table>
        </div>
        <div className="configItem-dividing" />
      </div>
    );
  }
}
