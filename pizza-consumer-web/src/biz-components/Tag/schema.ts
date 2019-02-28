export enum TagName {
  // header
  HEADER1 = 'HEADER1',
  HEADER2 = 'HEADER2',
  HEADER3 = 'HEADER3',
  HEADER4 = 'HEADER4',
  HEADER5 = 'HEADER5',

  // font
  FONT1 = 'FONT1',
  FONT2 = 'FONT2',

  // base
  TEXT1 = 'TEXT1',
  BLOCK1 = 'BLOCK1',
  IMAGE = 'IMAGE',
}

export enum TagBehavior {
  SIDEBAR = 'SIDEBAR',
  DISPLAY = 'DISPLAY',
}

export enum RowPosition {
  LEFT = 'LEFT',
  CENTER = 'CENTER',
  RIGHT = 'RIGHT',
}

export enum ColumPosition {
  TOP = 'TOP',
  CENTER = 'CENTER',
  BOTTOM = 'BOTTOM',
}

export interface Tag {
  tag_name: TagName;
  tag_props: any;
  tag_children: any;
  [props: string]: any;
}

export enum TagPropsEnum {
  height = 'height',
  width = 'width',
  row_position = 'row_position',
  column_position = 'column_position',
  font_size = 'font_size',
  left = 'left',
  top = 'top',
  content = 'content',
  contents = 'contents',
  color = 'color',
  background_color = 'background_color',
  font_weight = 'font_weight',
  overflow = 'overflow',
  number = 'number',
}
