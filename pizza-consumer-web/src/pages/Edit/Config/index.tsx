import * as React from 'react';
import './index.less';
import ConfigItem from './ConfigItem';
import { DisplayInfoEntity } from '@src/store/reducers/entity/display/schema';
import i18n from '@src/utils/i18n';

export interface ConfigProps {
  displayInfo: DisplayInfoEntity;
}

export interface ConfigState { }

export default class Config extends React.PureComponent<ConfigProps, ConfigState> {
  render() {
    const { displayInfo } = this.props;
    const { display, curr_display_id } = displayInfo;
    const curr_display = display[curr_display_id];
    const curr_display_children = curr_display && curr_display.tag_children || [];
    const curr_ids = curr_display_id ? curr_display_children.reduce((prev, curr) => {
      return prev.concat(curr.id);
    }, [curr_display_id]) : [];
    return (
      <div className="config-wrapper">
        {
          curr_ids.length ?
            curr_ids.map((id, idx) => (
              <ConfigItem
                key={idx}
                idx={idx}
                rootKey={curr_display_id}
                currTag={display[id]}
              />
            )) :
            <div className="config-bg">{i18n('配置区')}</div>
        }
      </div>
    );
  }
}
