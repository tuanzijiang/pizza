import * as React from 'react';
import {
  TagProps, TagState,
} from '@biz-components/Tag';
import { getPX } from '../index';
import { ColumPosition, RowPosition } from '@biz-components/Tag/schema';

interface CurrentTagProps extends TagProps {
  row_position: RowPosition;
  column_position: ColumPosition;
  font_size: number;
  content: string;
  color: string;
  width: number;
  height: number;
  left: number;
  top: number;
  font_weight: number;
  overflow: string;
  background_color: string;
}

export default class Text1 extends React.PureComponent<CurrentTagProps, TagState> {
  render() {
    const { tag_props } = this.props;
    const {
      row_position, column_position, font_size, content, color, width, height, top, left,
      font_weight, overflow, background_color,
    } = tag_props;

    const style = {
      width: getPX(width),
      height: getPX(height),
      fontSize: getPX(font_size),
      color: color || '#000',
      display: 'flex',
      position: 'absolute',
      fontWeight: font_weight || 500,
      overflow: overflow || 'hidden',
      top: getPX(top),
      left: getPX(left),
      alignItems: column_position === ColumPosition.CENTER ?
        'center' : (column_position === ColumPosition.TOP ? 'flex-start' : 'flex-end'),
      justifyContent: row_position === RowPosition.CENTER ?
        'center' : (row_position === RowPosition.LEFT ? 'flex-start' : 'flex-end'),
      backgroundColor: background_color || 'transparent',
    } as React.CSSProperties;
    return (
      <div style={style}>{content}</div>
    );
  }
}
