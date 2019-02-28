import * as React from 'react';
import { TagName } from '@biz-components/Tag/schema';
import { getPX as getBasePX } from '@utils/px';
import Header1, { config as Header1Config } from './Header1';
import Header2, { config as Header2Config } from './Header2';
import Header3, { config as Header3Config } from './Header3';
import Header4, { config as Header4Config } from './Header4';
import Header5, { config as Header5Config } from './Header5';
import Font1, { config as Font1Config } from './Font1';
import Font2, { config as Font2Config } from './Font2';
import Text1 from './Text1';
import Block1 from './Block1';
import { addDisplayInfo } from '@utils/store';

const BASE_WIDTH = 250;

const TagComponentMap: {
  [tag_name: string]: any,
} = {
  [TagName.HEADER1]: Header1,
  [TagName.HEADER2]: Header2,
  [TagName.HEADER3]: Header3,
  [TagName.HEADER4]: Header4,
  [TagName.HEADER5]: Header5,
  [TagName.FONT1]: Font1,
  [TagName.FONT2]: Font2,
  [TagName.TEXT1]: Text1,
  [TagName.BLOCK1]: Block1,
};

const TagConfigMap: {
  [tag_name: string]: any,
} = {
  [TagName.HEADER1]: Header1Config,
  [TagName.HEADER2]: Header2Config,
  [TagName.HEADER3]: Header3Config,
  [TagName.HEADER4]: Header4Config,
  [TagName.HEADER5]: Header5Config,
  [TagName.FONT1]: Font1Config,
  [TagName.FONT2]: Font2Config,
  [TagName.TEXT1]: null,
  [TagName.BLOCK1]: null,
};

export const getPX = (dist: number = 0) => {
  return getBasePX(dist, BASE_WIDTH);
};

export const getTagByName = (tag_name: TagName) => {
  return TagComponentMap[tag_name] || (() => <></>);
};

export const getClickByName = (tag_name: TagName) => {
  return () => {
    addDisplayInfo(TagConfigMap[tag_name]);
  };
};
