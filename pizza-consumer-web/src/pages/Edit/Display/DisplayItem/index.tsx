import * as React from 'react';
import './index.less';
import { DisplayEntity, DisplayInfoEntity } from '@src/store/reducers/entity/display/schema';
import TagComponent from '@biz-components/Tag';
import { TagBehavior, Tag } from '@biz-components/Tag/schema';
import autobind from 'autobind-decorator';

export interface DisplayItemProps {
  displayInfo: DisplayEntity;
  updateDisplayId(displayId: string): void;
}

export interface DisplayItemState { }

export default class DisplayItem extends React.PureComponent<DisplayItemProps, DisplayItemState> {
  @autobind
  handleClick(e: React.MouseEvent<HTMLDivElement>) {
    const { updateDisplayId, displayInfo } = this.props;
    const { id } = displayInfo;
    updateDisplayId(id);

    e.preventDefault();
    e.stopPropagation();
  }

  render() {
    const { displayInfo } = this.props;
    return (
      <div className="displayItem-wrapper">
        <div
          className="displayItem-behavior"
          onClick={this.handleClick}
        >
          <TagComponent
            {...displayInfo as Tag}
            behavior={TagBehavior.DISPLAY}
          />
        </div>
      </div>
    );
  }
}
