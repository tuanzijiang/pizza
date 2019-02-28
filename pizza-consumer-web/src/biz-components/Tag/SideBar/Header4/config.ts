import { TagName, RowPosition, ColumPosition } from '@biz-components/Tag/schema';

export const config = {
  tag_name: TagName.HEADER4,
  tag_props: {
    height: 20,
    width: 100,
  },
  tag_children: [{
    tag_name: TagName.BLOCK1,
    tag_props: {
      height: 2,
      width: 13,
      left: 23,
      top: 8.5,
    },
  }, {
    tag_name: TagName.BLOCK1,
    tag_props: {
      height: 2,
      width: 13,
      left: 64,
      top: 8.5,
    },
  }, {
    tag_name: TagName.TEXT1,
    tag_props: {
      height: 20,
      width: 100,
      row_position: RowPosition.CENTER,
      column_position: ColumPosition.CENTER,
      font_size: 8,
      left: 0,
      top: 0,
      content: '标题',
      color: '#666',
      font_weight: 600,
    },
  }],
};
