import { TagPropsEnum, RowPosition, ColumPosition } from './schema';
import i18n from '@src/utils/i18n';
import { InputType, InputInfo } from '@biz-components/Input/schema';

const PropsEnumInfoMap: InputInfo = {
  [TagPropsEnum.height]: {
    name: i18n('高度'),
    inputType: {
      type: InputType.STRING,
    },
  },
  [TagPropsEnum.width]: {
    name: i18n('宽度'),
    inputType: {
      type: InputType.STRING,
    },
  },
  [TagPropsEnum.row_position]: {
    name: i18n('水平对齐'),
    inputType: {
      type: InputType.SELECT,
      options: [
        RowPosition.LEFT,
        RowPosition.CENTER,
        RowPosition.RIGHT,
      ],
    },
  },
  [TagPropsEnum.column_position]: {
    name: i18n('垂直对齐'),
    inputType: {
      type: InputType.SELECT,
      options: [
        ColumPosition.TOP,
        ColumPosition.CENTER,
        ColumPosition.BOTTOM,
      ],
    },
  },
  [TagPropsEnum.font_size]: {
    name: i18n('字体大小'),
    inputType: {
      type: InputType.NUMBER,
    },
  },

  [TagPropsEnum.left]: {
    name: i18n('距左边'),
    inputType: {
      type: InputType.NUMBER,
    },
  },
  [TagPropsEnum.top]: {
    name: i18n('距顶部'),
    inputType: {
      type: InputType.NUMBER,
    },
  },
  [TagPropsEnum.color]: {
    name: i18n('字体颜色'),
    inputType: {
      type: InputType.COLOR,
    },
  },
  [TagPropsEnum.font_weight]: {
    name: i18n('字体粗细'),
    inputType: {
      type: InputType.SELECT,
      options: [
        '400',
        '500',
        '600',
      ],
    },
  },
  [TagPropsEnum.background_color]: {
    name: i18n('颜色'),
    inputType: {
      type: InputType.COLOR,
    },
  },
  [TagPropsEnum.content]: {
    name: i18n('内容'),
    inputType: {
      type: InputType.STRING,
    },
  },

  [TagPropsEnum.overflow]: {
    name: i18n('溢出处理'),
    inputType: {
      type: InputType.SELECT,
      options: [
        'hidden',
        'auto',
      ],
    },
  },

  // special
  [TagPropsEnum.contents]: {
    name: i18n('内容'),
    inputType: {
      type: InputType.STRING,
    },
  },
  [TagPropsEnum.number]: {
    name: i18n('个数'),
    inputType: {
      type: InputType.NUMBER,
    },
  },
};

export const getPropsEnumInfo = (prop_enum: TagPropsEnum) => PropsEnumInfoMap[prop_enum];
