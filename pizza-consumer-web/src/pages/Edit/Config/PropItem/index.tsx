import * as React from 'react';
import './index.less';
import { DisplayEntity } from '@src/store/reducers/entity/display/schema';
import { getPropsEnumInfo } from '@biz-components/Tag/config';
import { TagPropsEnum } from '@biz-components/Tag/schema';
import Input from '@biz-components/Input';

export interface PropItemProps {
  rootKey: string;
  currTag: DisplayEntity;
  propName: string;
  propValue: any;
}

export interface PropItemState { }

export default class PropItem extends React.PureComponent<PropItemProps, PropItemState> {
  render() {
    const { propName, propValue, rootKey, currTag } = this.props;
    const { id: currKey } = currTag;
    const propEnumInfo = getPropsEnumInfo(propName as TagPropsEnum);
    const propCnName = propEnumInfo && propEnumInfo.name || propName;
    return (
      <tr className="propItem-wrapper">
        <td className="propItem-name">{propCnName}</td>
        <td className="propItem-value">
          <Input
            value={propValue}
            config={propEnumInfo}
            propName={propName}
            propValue={propValue}
            rootKey={rootKey}
            currKey={currKey}
          />
        </td>
      </tr>
    );
  }
}
