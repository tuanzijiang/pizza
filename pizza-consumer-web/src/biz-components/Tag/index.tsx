import * as React from 'react';
import {
  getTagByName as getTagByNameOnSideBar,
  getClickByName as getClickByNameOnSideBar,
} from './SideBar';
import {
  getTagByName as getTagByNameOnDisplay,
} from './Display';
import { TagBehavior, TagName, Tag } from './schema';
import autobind from 'autobind-decorator';

export interface TagState { }

export interface TagProps extends Tag {
  behavior: TagBehavior;
  onClick?: (...param: any[]) => void;
  id?: string;
}

export const getTagByBehavior = (behavior: TagBehavior, tag_name: TagName): any => {
  if (behavior === TagBehavior.SIDEBAR) {
    return getTagByNameOnSideBar(tag_name);
  } else if (behavior === TagBehavior.DISPLAY) {
    return getTagByNameOnDisplay(tag_name);
  } else {
    return () => <></>;
  }
};

export const getClickByBehavior = (behavior: TagBehavior, tag_name: TagName): any => {
  if (behavior === TagBehavior.SIDEBAR) {
    return getClickByNameOnSideBar(tag_name);
  } else if (behavior === TagBehavior.DISPLAY) {
    return null;
  } else {
    return null;
  }
};

export default class TagComponent extends React.PureComponent<TagProps, TagState> {
  @autobind
  handleTagClick() {
    const { behavior, tag_name, onClick } = this.props;
    const TagClick = getClickByBehavior(behavior, tag_name);
    TagClick(onClick);
  }

  render() {
    const { behavior, tag_name } = this.props;
    const TagShadow = getTagByBehavior(behavior, tag_name);
    const TagClick = getClickByBehavior(behavior, tag_name);
    return (
      <TagShadow
        {...this.props}
        onClick={TagClick ? this.handleTagClick : null}
      />
    );
  }
}
