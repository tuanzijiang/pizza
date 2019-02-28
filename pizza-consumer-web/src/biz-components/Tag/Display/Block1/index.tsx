import * as React from 'react';
import {
  TagProps, TagState,
} from '@biz-components/Tag';
import { getPX } from '../index';
import { blue_1  } from '@ui/base';

interface CurrentTagProps extends TagProps{
  background_color: string;
  width: number;
  height: number;
  left: number;
  top: number;
  overflow: string;
}

export default class Text1 extends React.PureComponent<CurrentTagProps, TagState> {
  render() {
    const { tag_props } = this.props;
    const {
      backgroundColor, width, height, top, left, overflow,
    } = tag_props;

    const style = {
      width: getPX(width),
      height: getPX(height),
      backgroundColor: backgroundColor || blue_1,
      position: 'absolute',
      overflow: overflow || 'hidden',
      top: getPX(top),
      left: getPX(left),
      zIndex: 30,
    } as React.CSSProperties;
    return (
      <div style={style} />
    );
  }
}
