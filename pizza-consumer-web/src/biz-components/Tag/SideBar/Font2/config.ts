import { blue_1 } from '@src/ui/base';
import { TagName, RowPosition, ColumPosition } from '@biz-components/Tag/schema';

export const PER_HEIGHT = 20;

const generateChildrenJSON = (contents: string[]) => {
  const childrenJSON = contents.reduce((prev, curr, idx) => {
    prev.push({
      tag_name: TagName.TEXT1,
      tag_props: {
        height: 20,
        width: 85,
        row_position: RowPosition.LEFT,
        column_position: ColumPosition.CENTER,
        font_size: 7,
        left: 15,
        top: idx * PER_HEIGHT,
        content: curr,
        color: '#666',
        font_weight: 500,
      },
    });
    prev.push({
      tag_name: TagName.TEXT1,
      tag_props: {
        height: 13,
        width: 5,
        row_position: RowPosition.CENTER,
        column_position: ColumPosition.CENTER,
        font_size: 7,
        left: 7,
        top: idx * PER_HEIGHT + 3,
        content: idx + 1,
        color: '#666',
        background_color: blue_1,
        font_weight: 500,
      },
    });
    return prev;
  }, []);
  return childrenJSON;
};

export const config = {
  tag_name: TagName.FONT2,
  tag_props: {
    width: 100,
    number: 3,
    contents: ['列表选项', '列表选项'],
  },
  tag_children: [{}],
};

config.tag_children = generateChildrenJSON(config.tag_props.contents);
