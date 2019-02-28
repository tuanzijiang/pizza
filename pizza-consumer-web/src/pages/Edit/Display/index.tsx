import * as React from 'react';
import './index.less';
import { DisplayInfoEntity } from '@src/store/reducers/entity/display/schema';
import DisplayItem from './DisplayItem';
import autobind from 'autobind-decorator';

export interface DisplayProps {
  displayInfo: DisplayInfoEntity;
  updateDisplayId(displayId: string): void;
}

export interface DisplayState { }

export default class Display extends React.PureComponent<DisplayProps, DisplayState> {
  @autobind
  handleClick() {
    const { updateDisplayId } = this.props;
    updateDisplayId('');
  }

  render() {
    const { displayInfo, updateDisplayId } = this.props;
    const { display, display_order } = displayInfo;
    return (
      <div
        className="displayInfo-wrapper"
        onClick={this.handleClick}
      >
        <div className="displayInfo-content">
          {
            display_order.map(id => (
              <DisplayItem
                key={id}
                displayInfo={display[id]}
                updateDisplayId={updateDisplayId}
              />
            ))
          }
        </div>
      </div>
    );
  }
}
